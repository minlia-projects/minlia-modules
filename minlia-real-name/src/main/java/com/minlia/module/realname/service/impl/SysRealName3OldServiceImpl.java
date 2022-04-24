package com.minlia.module.realname.service.impl;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.realname.bean.SysRealName3OldDto;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.realname.config.SysRealNameAuthConfig;
import com.minlia.module.realname.constant.SysRealNameCode;
import com.minlia.module.realname.entity.SysRealNameEntity;
import com.minlia.module.realname.enums.SysAuthenticationStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 实名认证 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Slf4j
@Service
public class SysRealName3OldServiceImpl extends SysRealNameAbstractService {

    private RestTemplate restTemplate;

    public SysRealName3OldServiceImpl(SysRealNameAuthConfig sysRealNameAuthConfig, RestTemplate restTemplate) {
        super(sysRealNameAuthConfig);
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response auth(SysRealNameCro cro) {
        ApiAssert.state(!isAuthenticated(cro.getUid()), SysRealNameCode.Message.ALREADY_AUTHENTICATED);

        //调用第三方认证服务
        SysRealName3OldDto realNameDTO = auth(cro.getName(), cro.getIdNumber(), cro.getCellphone());

        //保存记录
        SysRealNameEntity entity = DozerUtils.map(cro, SysRealNameEntity.class);
        if (realNameDTO.isSuccess()) {
            Response response = null;
            //核验结果：1:一致。 2：不一致。3：核验异常（可联系客服查找具体原因）
            switch (realNameDTO.getState()) {
                case 1:
                    entity.setStatus(SysAuthenticationStatusEnum.PASSED);
                    this.save(entity);
                    response = Response.success(SysRealNameCode.Message.AUTHENTICATION_SUCCESSFUL);
                    break;
                case 2:
                    entity.setStatus(SysAuthenticationStatusEnum.FAILED);
                    this.save(entity);
                    response = Response.failure(SysRealNameCode.Message.IDENTITY_INCONSISTENCY);
                    break;
                case 3:
                    entity.setStatus(SysAuthenticationStatusEnum.FAILED);
                    this.save(entity);
                    response = Response.failure(SysRealNameCode.Message.INCORRECT_IDENTITY_INFORMATION);
                    break;
                default:
            }
            return response;
        } else {
            entity.setStatus(SysAuthenticationStatusEnum.FAILED);
            this.save(entity);
            return Response.failure(realNameDTO.getStatus(), Optional.ofNullable(realNameDTO.getReason()).orElse(realNameDTO.getResultMessage()));
        }
    }


    private final static String host = "https://dfphone3.market.alicloudapi.com";
    private final static String path = "/verify_id_name_phone";
    private final static String method = "POST";
    private final static Map<String, String> headers = Maps.newHashMap();

    {
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        //headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    }

    private SysRealName3OldDto auth(String name, String idNumber, String phone) {
        String appcode = getConfig().getAppCode();
        //String appcode = "a2dbd0ce06d0466aabd059823589afb9";
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("name", name);
        bodys.put("id_number", idNumber);
        bodys.put("phone_number", phone);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = com.minlia.module.realname.util.HttpUtils.doPost(host, path, method, headers, querys, bodys);
            ApiAssert.state(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK, SysRealNameCode.Message.CHANNEL_MORE_THAN_LIMIT);
            String body = EntityUtils.toString(response.getEntity());
            SysRealName3OldDto sysRealName3Dto = new Gson().fromJson(body, SysRealName3OldDto.class);
            return sysRealName3Dto;
        } catch (Exception e) {
            log.error("实名认证失败：", e);
            return SysRealName3OldDto.builder().status(SysRealNameCode.Message.CHANNEL_MORE_THAN_LIMIT.name()).reason(SysRealNameCode.Message.CHANNEL_MORE_THAN_LIMIT.message()).build();
        }
    }

    //public static void main(String[] args) {
    //    //SysRealName3OldDto dto = auth("谢海丽", "431128199810034223", "15574810006");    //一致
    //    //SysRealName3OldDto dto = auth("谢海丽", "431128199810034223", "19918844732");    //不一致
    //    SysRealName3OldDto dto = auth("廖文宇", "431128199610130018", "19918844732");  //一致
    //    //SysRealName3OldDto dto = auth("廖文宇", "431128199610130018", "17673601384");    //一致
    //    //SysRealName3OldDto dto = auth("侯志鹏", "430721199101136419", "18566297716");
    //    System.out.println(dto);
    //}

}