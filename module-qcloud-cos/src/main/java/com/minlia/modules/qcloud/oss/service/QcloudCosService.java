package com.minlia.modules.qcloud.oss.service;

import com.minlia.modules.qcloud.oss.config.QcloudCosConfig;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.transfer.Upload;

import java.io.File;
import java.io.InputStream;

/**
 * 腾讯云对象存储
 * Created by garen on 2018/6/6.
 */
public interface QcloudCosService {

    QcloudCosConfig getQcloudCosConfig();

    /**
     * 将本地文件上传到 COS
     * @param bucketName
     * @param key
     * @param file
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    PutObjectResult putObject(String bucketName, String key, File file) throws CosClientException, CosServiceException;

    /**
     * 输入流上传到 COS
     * @param bucketName
     * @param key
     * @param input
     * @param metadata
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws CosClientException, CosServiceException;

    /**
     * 对以上两个方法的包装, 支持更细粒度的参数控制, 如 content-type,  content-disposition 等
     * @param putObjectRequest
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    PutObjectResult putObject(PutObjectRequest putObjectRequest) throws CosClientException, CosServiceException;

    /**
     * 上传接口根据用户文件的长度自动选择简单上传以及分块上传， 降低用户的使用门槛。用户不用关心分块上传的每个步骤。
     * @param putObjectRequest
     * @return
     * @throws CosServiceException
     * @throws CosClientException
     */
    Upload upload(final PutObjectRequest putObjectRequest) throws CosServiceException, CosClientException;

}
