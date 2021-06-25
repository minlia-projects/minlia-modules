package com.minlia.aliyun.sts.bean;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliyunStsDto {

    /**
     * 接入端点
     * STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
     */
    private String endpoint;

    /**
     * 访问凭证的有效时间:秒
     */
    private Long durationSeconds;

    private String bucket;

    private AssumeRoleResponse.Credentials credentials;

}