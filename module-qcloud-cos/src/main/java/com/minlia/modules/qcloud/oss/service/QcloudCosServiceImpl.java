package com.minlia.modules.qcloud.oss.service;

import com.minlia.modules.qcloud.oss.client.QcloudCosClient;
import com.minlia.modules.qcloud.oss.config.QcloudCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * Created by garen on 2018/6/6.
 */
@Service
public class QcloudCosServiceImpl implements QcloudCosService {

    @Autowired
    private QcloudCosConfig qcloudCosConfig;

    @Override
    public QcloudCosConfig getQcloudCosConfig(){
        return this.qcloudCosConfig;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file) throws CosClientException {
        if (StringUtils.isEmpty(bucketName)) {
            bucketName = qcloudCosConfig.getBucketName();
        }
        return QcloudCosClient.cosClient().putObject(bucketName, key, file);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws CosClientException {
        if (StringUtils.isEmpty(bucketName)) {
            bucketName = qcloudCosConfig.getBucketName();
        }
        return QcloudCosClient.cosClient().putObject(bucketName, key, input, metadata);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws CosClientException {
        if (StringUtils.isEmpty(putObjectRequest.getBucketName())) {
            putObjectRequest.setBucketName(qcloudCosConfig.getBucketName());
        }
        return QcloudCosClient.cosClient().putObject(putObjectRequest);
    }

    @Override
    public Upload upload(PutObjectRequest putObjectRequest) throws CosClientException {
        return null;
    }

}
