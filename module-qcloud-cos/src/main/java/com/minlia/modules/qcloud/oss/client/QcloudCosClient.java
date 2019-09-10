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
            // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region(properties.getRegion());
            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端。
            cosClient = new COSClient(cred, clientConfig);
        }
        return cosClient;
    }

}
