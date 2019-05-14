package com.minlia.modules.attachment.service.impl;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.ro.AttachmentUploadRO;
import com.minlia.modules.attachment.dto.AttachmentDTO;
import com.minlia.modules.attachment.constant.AttachmentCode;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.property.AttachmentProperties;
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

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class AttachmentUploadServiceImpl implements AttachmentUploadService {

    @Autowired
    private OssService ossService;

    @Autowired
    private QcloudCosService qcloudCosService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentProperties attachmentProperties;

    @Override
    public Response upload(MultipartFile file) throws Exception {
        return upload(file, null, null);
    }

    @Override
    public Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception {
        switch (attachmentProperties.getType()) {
            case aliyun:
                return uploadByAliyun(file, relationId, belongsTo);
            case qcloud:
                return uploadByQcloud(file, relationId, belongsTo);
            case local:
                return uploadByLocal(file, relationId, belongsTo);
        }
        return Response.failure(AttachmentCode.Message.UNSUPPORTED_OSS_TYPE);
    }

    private Response uploadByQcloud(MultipartFile file, String relationId, String belongsTo) throws Exception {
        String path = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, belongsTo);
        PutObjectResult result = qcloudCosService.putObject(null,path,file.getInputStream(), QcloudCosUtils.createDefaultObjectMetadata(file));
        OssFile ossFile= new OssFile(result.getETag());
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

    private Response uploadByAliyun(MultipartFile file, String relationId, String belongsTo){
        log.info(file.getContentType());
        log.info(file.getOriginalFilename());

        String key = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, belongsTo);
        OssFile ossFile=null;
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

    private Response uploadByLocal(MultipartFile file, String relationId, String belongsTo){
        String path = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, belongsTo);
        String filePath = attachmentProperties.getLocal().get("bucket") + path;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));//保存上传的文件到指定路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        Attachment attachment = Attachment.builder()
                .relationId(relationId)
                .belongsTo(belongsTo)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(path)
                .size(file.getSize())
                .accessKey(NumberGenerator.uuid32())
                .build();
        attachmentService.create(attachment);

        AttachmentDTO dto = AttachmentDTO.builder()
                .url(path)
                .eTag(attachment.getAccessKey())
                .fileName(file.getName())
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
        return Response.success(dto);
    }

    @Override
    public Response upload(AttachmentUploadRO to) {
        if (StringUtils.isEmpty(to.getKey())){
            to.setKey(OSSPathUtils.getDefaultPath(to.getFile().getName()));
        }

        PutObjectResult result = qcloudCosService.putObject(null,to.getKey(),to.getFile());

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

        OssFile ossFile= new OssFile(result.getETag());
        ossFile.setContentType(attachment.getType());
        ossFile.setName(attachment.getName());
        ossFile.setSize(attachment.getSize());
        ossFile.setUrl(attachment.getUrl());
        //附件记录
        attachmentService.create(attachment);
        return Response.success(ossFile);
    }

}