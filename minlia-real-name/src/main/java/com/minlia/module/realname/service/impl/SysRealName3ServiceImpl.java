package com.minlia.module.realname.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.realname.bean.SysRealName3Dto;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
public class SysRealName3ServiceImpl extends SysRealNameAbstractService {

    private final RestTemplate restTemplate;

    public SysRealName3ServiceImpl(SysRealNameAuthConfig sysRealNameAuthConfig, RestTemplate restTemplate) {
        super(sysRealNameAuthConfig);
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response auth(SysRealNameCro cro) {
        ApiAssert.state(!isAuthenticated(cro.getUid()), SysRealNameCode.Message.ALREADY_AUTHENTICATED);

        //判断大于14岁
        ApiAssert.state(IdcardUtil.getAgeByIdCard(cro.getIdNumber()) >= 18, SysRealNameCode.Message.AGE_MUST_BE_OVER18);

        //调用第三方认证服务
        SysRealName3Dto realNameDTO = auth(cro.getName(), cro.getIdNumber(), cro.getCellphone());

        //保存记录
        SysRealNameEntity entity = DozerUtils.map(cro, SysRealNameEntity.class);

        if (realNameDTO.isSuccess()) {
            Response response = null;
            //1 一致；2 不一致；3 无记录
            switch (realNameDTO.getData().getBizCode()) {
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
            return Response.failure(realNameDTO.getCode().toString(), realNameDTO.getMessage());
        }
    }

    private final static String host = "https://mobilecert.market.alicloudapi.com";
    private final static String path = "/mobile3MetaSimple";
    private final static String method = "GET";
    private final static Map<String, String> headers = Maps.newHashMap();

    private SysRealName3Dto auth(String name, String idNumber, String phone) {
        String appcode = getConfig().getAppCode();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("identifyNum", idNumber);
        querys.put("mobile", phone);
        querys.put("userName", name);

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
            HttpResponse response = com.minlia.module.realname.util.HttpUtils.doGet(host, path, method, headers, querys);
            ApiAssert.state(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK, SysRealNameCode.Message.CHANNEL_MORE_THAN_LIMIT);
            String body = EntityUtils.toString(response.getEntity());
            SysRealName3Dto sysRealName3Dto = new Gson().fromJson(body, SysRealName3Dto.class);
            return sysRealName3Dto;
        } catch (Exception e) {
            log.error("实名认证失败：", e);
            return SysRealName3Dto.builder().code(500).message(SysRealNameCode.Message.CHANNEL_MORE_THAN_LIMIT.message()).build();
        }
    }

    public static void main(String[] args) {
        String host = "https://mobilecert.market.alicloudapi.com";
        String path = "/mobile3MetaSimple";
        String method = "GET";
        String appcode = "你自己的AppCode";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + "a2dbd0ce06d0466aabd059823589afb9");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("identifyNum", "430721199101136419");
        //querys.put("identifyNumMd5", "identifyNumMd5");
        //querys.put("mobile", "18566297716");D
        querys.put("mobile", "18888888888");
        //querys.put("mobileMd5", "mobileMd5");
        querys.put("userName", "侯志鹏");
        //querys.put("userNameMd5", "userNameMd5");

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
            HttpResponse response = com.minlia.module.realname.util.HttpUtils.doGet(host, path, method, headers, querys);
            ApiAssert.state(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK, SysRealNameCode.Message.CHANNEL_MORE_THAN_LIMIT);
            String body = EntityUtils.toString(response.getEntity());
            SysRealName3Dto sysRealName3Dto = new Gson().fromJson(body, SysRealName3Dto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}