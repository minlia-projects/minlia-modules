package com.minlia.module.realname.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.realname.bean.SysRealName2Dto;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.realname.config.SysRealNameAuthConfig;
import com.minlia.module.realname.constant.SysRealNameCode;
import com.minlia.module.realname.entity.SysRealNameEntity;
import com.minlia.module.realname.enums.SysAuthenticationStatusEnum;
import com.minlia.module.realname.event.SysRealNameSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
public class SysRealName2ServiceImpl extends SysRealNameAbstractService {

    private RestTemplate restTemplate;

    public SysRealName2ServiceImpl(SysRealNameAuthConfig sysRealNameAuthConfig, RestTemplate restTemplate) {
        super(sysRealNameAuthConfig);
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response auth(SysRealNameCro cro) {
        ApiAssert.state(!isAuthenticated(cro.getUid()), SysRealNameCode.Message.ALREADY_AUTHENTICATED);

        //判断大于14岁
        ApiAssert.state(IdcardUtil.getAgeByIdCard(cro.getIdNumber()) >= 18, SysRealNameCode.Message.AGE_MUST_BE_OVER18);

        //调用第三方认证服务 TODO
        SysRealName2Dto realNameDTO = auth(cro.getName(), cro.getIdNumber());

        //保存记录
        SysRealNameEntity entity = DozerUtils.map(cro, SysRealNameEntity.class);

        if (realNameDTO.isSuccess()) {
            Response response = null;
            //1 一致；2 不一致；3 无记
            switch (realNameDTO.getResult().getRes()) {
                case 1:
                    entity.setStatus(SysAuthenticationStatusEnum.PASSED);
                    this.save(entity);
                    //发送成功事件 TODO
                    SysRealNameSuccessEvent.publish(realNameDTO);
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

    @Override
    public boolean isAuthenticated(Long uid) {
        return this.count(Wrappers.<SysRealNameEntity>lambdaQuery().eq(SysRealNameEntity::getUid, uid).eq(SysRealNameEntity::getStatus, SysAuthenticationStatusEnum.PASSED)) > 0;
    }

    @Override
    public SysRealNameEntity getAuthenticatedByUid(Long uid) {
        return this.getOne(Wrappers.<SysRealNameEntity>lambdaQuery().eq(SysRealNameEntity::getUid, uid).eq(SysRealNameEntity::getStatus, SysAuthenticationStatusEnum.PASSED));
    }

    private SysRealName2Dto auth(String name, String idNumber) {
        //设置请求参数
        MultiValueMap<String, Object> querys = new LinkedMultiValueMap<>();
        querys.add("idcard", idNumber);
        querys.add("name", name);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "APPCODE " + getConfig().getAppCode());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(querys, headers);
        SysRealName2Dto realNameDTO = restTemplate.postForObject(getConfig().getUrl(), httpEntity, SysRealName2Dto.class);
        return realNameDTO;
    }

}