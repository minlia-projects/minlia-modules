package com.minlia.module.attachment.service.impl;


import com.minlia.aliyun.green.bean.AliyunGreenImageResult;
import com.minlia.aliyun.green.constant.AliyunGreenCode;
import com.minlia.aliyun.green.service.AliyunGreenImageService;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.attachment.constant.SysAttachmentCode;
import com.minlia.module.attachment.bean.AttachmentDto;
import com.minlia.module.attachment.bean.AttachmentUploadBody;
import com.minlia.module.attachment.bean.AttachmentUploadRo;
import com.minlia.module.attachment.entity.SysAttachmentEntity;
import com.minlia.module.attachment.service.AttachmentService;
import com.minlia.module.attachment.service.AttachmentUploadService;
import com.minlia.module.attachment.util.ContentTypeUtils;
import com.minlia.module.attachment.util.OSSPathUtils;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.module.attachment.property.AttachmentConfig;
import com.minlia.module.attachment.property.AttachmentLocalConfig;
import com.minlia.modules.qcloud.oss.service.QcloudCosService;
import com.minlia.modules.qcloud.oss.util.QcloudCosUtils;
import com.qcloud.cos.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentUploadServiceImpl implements AttachmentUploadService {

    private final OssService ossService;
    private final QcloudCosService qcloudCosService;
    private final AttachmentService attachmentService;
    private final AttachmentConfig attachmentConfig;
    private final AttachmentLocalConfig attachmentLocalConfig;
    private final AliyunGreenImageService aliyunGreenImageService;

    @Override
    public Response upload(MultipartFile file) throws Exception {
        return upload(file, null, null);
    }

    @Override
    public Response upload(MultipartFile file, String relationId, String relationTo) throws Exception {
        switch (attachmentConfig.getChannel()) {
            case ALIYUN:
                return uploadByAliyun(file, relationId, relationTo);
            case QCLOUD:
                return uploadByQcloud(file, relationId, relationTo);
            case LOCAL:
                return uploadByLocal(file, relationId, relationTo);
        }
        return Response.failure(SysAttachmentCode.Message.UNSUPPORTED_OSS_TYPE);
    }

    private Response uploadByQcloud(MultipartFile file, String relationId, String relationTo) throws Exception {
        String path = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, relationTo);
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

        SysAttachmentEntity attachment = SysAttachmentEntity.builder()
                .relationTo(relationTo)
                .relationId(relationId)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(ossFile.getUrl())
                .size(file.getSize())
                .accessKey(result.getETag())
                .build();
        attachmentService.save(attachment);
        return Response.success(ossFile);
    }

    private Response uploadByAliyun(MultipartFile file, String relationId, String relationTo) {
        log.info(file.getContentType());
        log.info(file.getOriginalFilename());

        String key = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, relationTo);
        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file, key);
        } catch (Exception e) {
            ApiAssert.state(false, SystemCode.Exception.REMOTE_REQUEST_FAILURE, e.getMessage());
        }

        SysAttachmentEntity attachment = SysAttachmentEntity.builder()
                .relationId(relationId)
                .relationTo(relationTo)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(ossFile.getUrl())
                .size(ossFile.getSize())
                .accessKey(ossFile.geteTag())
                .build();
        attachmentService.save(attachment);

        //if (file.getContentType().contains("image")) {
        //    AliyunGreenImageResult aliyunGreenImageResult = aliyunGreenImageService.handle("https:" + ossFile.getUrl());
        //    if (aliyunGreenImageResult.isBlock()) {
        //        return Response.failure(AliyunGreenCode.Message.valueOf(aliyunGreenImageResult.getLabel()));
        //    }
        //}
        return Response.success(ossFile);
    }

    private Response uploadByLocal(MultipartFile file, String relationId, String relationTo) {
        String path = OSSPathUtils.getPath(file.getOriginalFilename(), relationId, relationTo);
        String filePath = attachmentLocalConfig.getBucket() + path;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath)); //保存上传的文件到指定路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = attachmentLocalConfig.getHost() + attachmentLocalConfig.getPathPatterns() + path;

        SysAttachmentEntity attachment = SysAttachmentEntity.builder()
                .relationId(relationId)
                .relationTo(relationTo)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
//                .url(path)
                .url(url)
                .size(file.getSize())
                .accessKey(NumberGenerator.uuid32())
                .build();
        attachmentService.save(attachment);

        AttachmentDto dto = AttachmentDto.builder()
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
    public Response uploadByBase64ToLocal(AttachmentUploadBody uploadBody, String relationId, String relationTo) {
        String path = OSSPathUtils.getPath(uploadBody.getOriginalFilename(), relationId, relationTo);
        String filePath = attachmentLocalConfig.getBucket() + path;
        try {
            FileUtils.copyInputStreamToFile(uploadBody.getInputStream(), new File(filePath)); //保存上传的文件到指定路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = attachmentLocalConfig.getHost() + attachmentLocalConfig.getPathPatterns() + path;

        SysAttachmentEntity attachment = SysAttachmentEntity.builder()
                .relationId(relationId)
                .relationTo(relationTo)
                .name(uploadBody.getOriginalFilename())
                .type(uploadBody.getContentType())
                .url(url)
                .size(uploadBody.getSize())
                .accessKey(NumberGenerator.uuid32())
                .build();
        attachmentService.save(attachment);

        AttachmentDto dto = AttachmentDto.builder()
                .url(url)
                .eTag(attachment.getAccessKey())
                .fileName(uploadBody.getFileName())
                .originalFilename(uploadBody.getOriginalFilename())
                .contentType(uploadBody.getContentType())
                .size(uploadBody.getSize())
                .build();
        return Response.success(dto);
    }


    @Override
    public Response upload(AttachmentUploadRo to) {
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
        SysAttachmentEntity attachment = SysAttachmentEntity.builder()
                .relationTo(to.getBelongsTo())
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
        attachmentService.save(attachment);
        return Response.success(ossFile);
    }

}
