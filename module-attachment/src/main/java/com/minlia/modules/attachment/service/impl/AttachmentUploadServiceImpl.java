package com.minlia.modules.attachment.service.impl;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.constant.AttachmentCode;
import com.minlia.modules.attachment.dto.AttachmentDTO;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.property.AttachmentConfig;
import com.minlia.modules.attachment.property.AttachmentLocalConfig;
import com.minlia.modules.attachment.ro.AttachmentBase64UploadRO;
import com.minlia.modules.attachment.ro.AttachmentUploadRO;
import com.minlia.modules.attachment.service.AttachmentService;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import com.minlia.modules.attachment.util.ContentTypeUtils;
import com.minlia.modules.attachment.util.OSSPathUtils;
import com.minlia.modules.qcloud.oss.service.QcloudCosService;
import com.minlia.modules.qcloud.oss.util.QcloudCosUtils;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AttachmentUploadServiceImpl implements AttachmentUploadService {

    @Autowired
    private OssService ossService;

    @Autowired
    private QcloudCosService qcloudCosService;

    @Autowired
    private AttachmentService attachmentService;

//    @Autowired
//    private AttachmentProperties attachmentProperties;

    @Autowired
    private AttachmentConfig attachmentConfig;

    @Autowired
    private AttachmentLocalConfig attachmentLocalConfig;

    @Override
    public Response upload(MultipartFile file) throws Exception {
        return upload(file, null, null);
    }

    @Override
    public Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception {
        switch (attachmentConfig.getChannel()) {
            case ALIYUN:
                return uploadByAliyun(file, relationId, belongsTo);
            case QCLOUD:
                return uploadByQcloud(file, relationId, belongsTo);
            case LOCAL:
                return uploadByLocal(file, relationId, belongsTo);
        }
        return Response.failure(AttachmentCode.Message.UNSUPPORTED_OSS_TYPE);
    }

    private Response uploadByQcloud(MultipartFile file, String relationId, String belongsTo) throws Exception {
        String path = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, belongsTo);
        PutObjectResult result = qcloudCosService.putObject(null, path, file.getInputStream(), QcloudCosUtils.createDefaultObjectMetadata(file));
        OssFile ossFile = new OssFile(result.getETag());
        ossFile.setContentType(file.getContentType());
        ossFile.setName(file.getOriginalFilename());
        ossFile.setSize(file.getSize());
        if (null != qcloudCosService.getQcloudCosConfig().getImageDomain() && ContentTypeUtils.isImage(file)) {
            ossFile.setUrl(qcloudCosService.getQcloudCosConfig().getImageDomain() + path);
        } else {
            ossFile.setUrl(qcloudCosService.getQcloudCosConfig().getDomain() + path);
        }

        Attachment attachment = Attachment.builder()
                .relationId(relationId)
                .belongsTo(belongsTo)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(ossFile.getUrl())
                .size(file.getSize())
                .accessKey(result.getETag())
                .build();
        attachmentService.create(attachment);
        return Response.success(ossFile);
    }

    private Response uploadByAliyun(MultipartFile file, String relationId, String belongsTo) {
        String key = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, belongsTo);
        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file, key);
        } catch (Exception e) {
            ApiAssert.state(false, SystemCode.Exception.REMOTE_REQUEST_FAILURE, e.getMessage());
        }

        Attachment attachment = Attachment.builder()
                .relationId(relationId)
                .belongsTo(belongsTo)
                .name(ossFile.getOriginalName())
                .type(ossFile.getContentType())
                .url(ossFile.getUrl())
                .size(ossFile.getSize())
                .accessKey(ossFile.geteTag())
                .build();
        attachmentService.create(attachment);
        return Response.success(ossFile);
    }

    private Response uploadByLocal(MultipartFile file, String relationId, String belongsTo) {
        String path = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, belongsTo);
        String filePath = attachmentLocalConfig.getBucket() + path;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath)); //保存上传的文件到指定路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = attachmentLocalConfig.getHost() + attachmentLocalConfig.getPathPatterns() + path;

        Attachment attachment = Attachment.builder()
                .relationId(relationId)
                .belongsTo(belongsTo)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
//                .url(path)
                .url(url)
                .size(file.getSize())
                .accessKey(NumberGenerator.uuid32())
                .build();
        attachmentService.create(attachment);

        AttachmentDTO dto = AttachmentDTO.builder()
//                .url(path)
                .url(url)
                .eTag(attachment.getAccessKey())
                .fileName(file.getName())
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
        return Response.success(dto);
    }

    @Override
    public Response uploadByBase64(AttachmentBase64UploadRO uploadRO, String relationId, String belongsTo) throws Exception {
        //将字符串转换为byte数组
        byte[] bytes = new BASE64Decoder().decodeBuffer(uploadRO.getFile().trim());
        //转化为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        uploadRO.setInputStream(inputStream);
//        uploadRO.setSize(Long.valueOf(bytes.length));

        OssFile ossFile = null;

        switch (attachmentConfig.getChannel()) {
            case ALIYUN:
                ossFile = uploadByBase64ToAliyun(inputStream, uploadRO.getOriginalFilename(), relationId, belongsTo);
                break;
            case QCLOUD:
                ossFile = uploadByBase64ToQcloud(inputStream, uploadRO.getOriginalFilename(), relationId, belongsTo);
                break;
            case LOCAL:
                ossFile = uploadByBase64ToLocal(inputStream, uploadRO.getOriginalFilename(), relationId, belongsTo);
                break;
        }
        Attachment attachment = Attachment.builder()
                .relationId(relationId)
                .belongsTo(belongsTo)
                .name(uploadRO.getOriginalFilename())
                .type(uploadRO.getContentType())
                .url(ossFile.getUrl())
                .size(Long.valueOf(bytes.length))
                .accessKey(ossFile.geteTag())
                .build();
        attachmentService.create(attachment);

        AttachmentDTO dto = AttachmentDTO.builder()
                .fileName(ossFile.getName())
                .url(ossFile.getUrl())
                .size(Long.valueOf(bytes.length))
                .eTag(ossFile.geteTag())
                .originalFilename(uploadRO.getOriginalFilename())
                .contentType(uploadRO.getContentType())
                .build();
        return Response.success(dto);
    }

    public OssFile uploadByBase64ToAliyun(InputStream inputStream, String originalFileName, String relationId, String belongsTo) {
        String path = OSSPathUtils.getPath(originalFileName, relationId, belongsTo);
        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(inputStream, path);
        } catch (Exception e) {
            ApiAssert.state(false, SystemCode.Exception.REMOTE_REQUEST_FAILURE, e.getMessage());
        }
        return ossFile;
    }

    public OssFile uploadByBase64ToQcloud(InputStream inputStream, String originalFileName, String relationId, String belongsTo) {
        String path = OSSPathUtils.getPath(originalFileName, relationId, belongsTo);
        PutObjectResult result = qcloudCosService.putObject(null, path, inputStream, null);
        OssFile ossFile = new OssFile(result.getETag());
        ossFile.setName(originalFileName);
//        if (null != qcloudCosService.getQcloudCosConfig().getImageDomain() && ContentTypeUtils.isImage(file)) {
//            ossFile.setUrl(qcloudCosService.getQcloudCosConfig().getImageDomain() + path);
//        } else {
//            ossFile.setUrl(qcloudCosService.getQcloudCosConfig().getDomain() + path);
//        }
        return ossFile;
    }

    public OssFile uploadByBase64ToLocal(InputStream inputStream, String originalFileName, String relationId, String belongsTo) {
        String path = OSSPathUtils.getPath(originalFileName, relationId, belongsTo);
        String filePath = attachmentLocalConfig.getBucket() + path;
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(filePath)); //保存上传的文件到指定路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = attachmentLocalConfig.getHost() + attachmentLocalConfig.getPathPatterns() + path;

        OssFile ossFile = new OssFile(NumberGenerator.uuid32());
        ossFile.setUrl(url);
        return ossFile;
    }

    @Override
    public Response upload(AttachmentUploadRO to) {
        if (StringUtils.isEmpty(to.getKey())) {
            to.setKey(OSSPathUtils.getDefaultPath(to.getFile().getName()));
        }

        PutObjectResult result = qcloudCosService.putObject(null, to.getKey(), to.getFile());

        String url;
        if (null != qcloudCosService.getQcloudCosConfig().getImageDomain() && ContentTypeUtils.isImage(to.getFile())) {
            url = qcloudCosService.getQcloudCosConfig().getImageDomain() + to.getKey();
        } else {
            url = qcloudCosService.getQcloudCosConfig().getDomain() + to.getKey();
        }
        Attachment attachment = Attachment.builder()
                .belongsTo(to.getBelongsTo())
                .relationId(to.getRelationId())
                .name(to.getFile().getName())
                .type(ContentTypeUtils.getExtension(to.getFile().getName()))
                .url(url)
                .size(to.getFile().length())
                .accessKey(result.getETag())
                .build();

        OssFile ossFile = new OssFile(result.getETag());
        ossFile.setContentType(attachment.getType());
        ossFile.setName(attachment.getName());
        ossFile.setSize(attachment.getSize());
        ossFile.setUrl(attachment.getUrl());
        //附件记录
        attachmentService.create(attachment);
        return Response.success(ossFile);
    }

}
