package com.minlia.aliyun.sts.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.minlia.aliyun.sts.config.AliyunStsConfig;
import com.minlia.cloud.utils.ApiAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author garen
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunStsService {

    private final AliyunStsConfig aliyunStsConfig;
    private static DefaultAcsClient client = null;
    private static AssumeRoleRequest request = null;

    @PostConstruct
    public void init() {
        if (Objects.nonNull(aliyunStsConfig) && aliyunStsConfig.getEnable()) {
            DefaultProfile.addEndpoint("", "Sts", aliyunStsConfig.getEndpoint());
            IClientProfile profile = DefaultProfile.getProfile("", aliyunStsConfig.getAccessKeyId(), aliyunStsConfig.getAccessKeySecret());
            client = new DefaultAcsClient(profile);
            request = initRequest();
        }
    }

    private AssumeRoleRequest initRequest() {
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysMethod(MethodType.POST);
        // 填写步骤3获取的角色ARN。
        request.setRoleArn(aliyunStsConfig.getRoleArn());
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        request.setRoleSessionName("SessionRamoss");
//        request.setPolicy(policy);    // 如果policy为空，则用户将获得该角色下所有权限。
        // 设置临时访问凭证的有效时间为3600秒。
        request.setDurationSeconds(aliyunStsConfig.getDurationSeconds());
        return request;
    }

    /**
     * 获取凭证
     *
     * @return 凭证信息
     */
    public AssumeRoleResponse.Credentials getCredentials() {
        try {
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response.getCredentials();
        } catch (ClientException e) {
            log.error("获取临时访问凭证失败：{0}", e);
            ApiAssert.state(false, e.getErrCode(), e.getErrMsg());
            return null;
        }
    }

}