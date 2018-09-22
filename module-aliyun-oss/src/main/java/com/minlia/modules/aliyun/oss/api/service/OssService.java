package com.minlia.modules.aliyun.oss.api.service;

import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssProperties;
import com.minlia.modules.aliyun.oss.api.constant.AliyunOssCode;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.Constant;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


//@Service
public class OssService implements InitializingBean {

    @Autowired
    private AliyunOssProperties properties;
    private String endpoint;
    private String accessId;
    private String accessKey;
    private String bucket;

    private boolean detectContentType = false;
    public static final String ORIGIN_ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";

    public OssFile upload(MultipartFile file) throws IOException {
        return upload(file,  PathBuilder.dateBuild() + PathBuilder.uuidNameBuild(file.getOriginalFilename()));
    }

    public OssFile upload(MultipartFile file, String key) throws IOException {
        return upload(file, createDefaultObjectMetadata(file), key);
    }

    public OssFile upload(MultipartFile file, ObjectMetadata metadata, String key) throws IOException {
        OssFile ossFile = ossUpload(key, file.getInputStream(), metadata);
        ossFile.setName(key);
        ossFile.setOriginalName(file.getOriginalFilename());
        return ossFile;
    }

    public OssFile upload(File file, String key) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        OssFile ossFile = ossUpload(key, inputStream, createDefaultObjectMetadata(file));
        ossFile.setName(key);
        ossFile.setOriginalName(file.getName());
        return ossFile;
    }

    private OssFile ossUpload(String key, InputStream inputStream, ObjectMetadata metadata) {
        OssFile result = null;
        try {
            PutObjectResult putObjectResult = AliyunOssClient.ossClient().putObject(bucket, key, inputStream, metadata);
            result = new OssFile(putObjectResult.getETag());
            result.setUrl(builderUrl(key));
            result.setSize(metadata.getContentLength());
            result.setContentType(metadata.getContentType());
        } catch (OSSException e) {
            ApiAssert.state(false, AliyunOssCode.Message.UPLOAD_FAILURE, e.getMessage());
        } catch (Exception e) {
            ApiAssert.state(false, AliyunOssCode.Message.UPLOAD_FAILURE, e.getMessage());
        }
        return result;
    }

    private ObjectMetadata createDefaultObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        if (detectContentType) {
            // TODO
        } else {
            metadata.setContentType(file.getContentType());
        }
        return metadata;
    }

    private ObjectMetadata createDefaultObjectMetadata(File file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        if (detectContentType) {
            // TODO
        } else {
            metadata.setContentType("");
        }
        return metadata;
    }

    private String builderUrl(String key){
        String url;
        if (ORIGIN_ENDPOINT.equals(properties.getEndpoint())) {
            url=String.format(Constant.OLD_RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),key);
        } else {
            url=String.format(Constant.RETURN_URL_PATTERN,properties.getEndpoint(),key);
        }
        return url;
    }

    @Override
    public void afterPropertiesSet() {
//        this.accessId = properties.getKey();
//        this.accessKey = properties.getSecret();
//        this.bucket = properties.getBucket();
//        this.endpoint = properties.getEndpoint();
//
//        if (endpoint == null) {
//            endpoint = Constant.DEFAULT_OSS_ENDPOINT;
//        } else if (endpoint.endsWith(Constant.SLASH)) {
//            endpoint = endpoint.replaceAll("/*$", Constant.EMPTY);
//        }
//
//        Assert.hasText(accessId, "请指定accessId。\n" + Constant.API_KEY_HELP);
//        Assert.hasText(accessKey, "请指定accessKey。\n" + Constant.API_KEY_HELP);
//        Assert.hasText(bucket, "请指定bucket。\n" + Constant.BUCKET_HELP);
//
//        ossClient = new OSSClient(ORIGIN_ENDPOINT, accessId, accessKey);
//        try {
//            ossClient.getBucketAcl(bucket);
//            // TODO 校验ACL
//        } catch (OSSException e) {
//            String errorCode = e.getErrorCode();
//            if (OSSErrorCode.INVALID_ACCESS_KEY_ID.equals(errorCode)) {
//                Assert.isTrue(false, "请指定正确的accessId。\n" + Constant.API_KEY_HELP);
//            } else if (OSSErrorCode.SIGNATURE_DOES_NOT_MATCH.equals(errorCode)) {
//                Assert.isTrue(false, "请指定正确的accessKey。\n" + Constant.API_KEY_HELP);
//            } else if (OSSErrorCode.NO_SUCH_BUCKET.equals(errorCode)) {
//                Assert.isTrue(false, "请指定一个存在的Bucket。");
//            } else {
//                Assert.isTrue(false, errorCode);
//            }
//        } catch (Exception e) {
//            // Ignore
//            e.printStackTrace();
//        }
    }

}
