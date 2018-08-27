package com.minlia.modules.qcloud.oss.client;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.qcloud.oss.config.QcloudCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

/**
 * Created by garen on 2018/8/27.
 */
public class QcloudCosClient {

    private static COSClient cosClient;

    public static COSClient cosClient(){
        if (null == cosClient) {
            QcloudCosConfig properties = ContextHolder.getContext().getBean(QcloudCosConfig.class);
            // 1 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials(properties.getAccessKey(), properties.getSecretKey());
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region(properties.getRegion()));
            // 3 生成cos客户端
            cosClient = new COSClient(cred, clientConfig);
            // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式w
            String bucketName = properties.getBucketName();
        }
        return cosClient;
    }

}
