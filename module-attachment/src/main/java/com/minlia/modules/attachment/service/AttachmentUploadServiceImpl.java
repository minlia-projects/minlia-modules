package com.minlia.modules.attachment.service;


import com.google.common.collect.Lists;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import com.minlia.modules.attachment.body.AttachmentUploadRequestBody;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.util.ContentTypeUtils;
import com.minlia.modules.attachment.util.CosPathUtils;
import com.minlia.modules.qcloud.oss.service.QcloudCosService;
import com.minlia.modules.qcloud.oss.util.QcloudCosUtils;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
public class AttachmentUploadServiceImpl implements AttachmentUploadService {

    @Autowired
    private OssService ossService;

    @Autowired
    private QcloudCosService qcloudCosService;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public Response upload(MultipartFile file) throws Exception {
        String path = CosPathUtils.defaultBuild(file.getOriginalFilename());
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
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(ossFile.getUrl())
                .size(file.getSize())
                .accessKey(result.getETag())
                .build();
        attachmentService.create(attachment);
        return Response.success(ossFile);
    }

    @Override
    public Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception {
        String path = CosPathUtils.defaultBuild(file.getOriginalFilename());

        PutObjectResult result = qcloudCosService.putObject(null,path,file.getInputStream(), QcloudCosUtils.createDefaultObjectMetadata(file));
        Attachment attachment = Attachment.builder()
                .belongsTo(belongsTo)
                .relationId(relationId)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(qcloudCosService.getQcloudCosConfig().getDomain() + path)
                .size(file.getSize())
                .accessKey(result.getETag())
                .build();

        //附件记录
        attachmentService.create(attachment);
        return Response.success(Lists.newArrayList(attachment));
    }

    @Override
    public Response upload(AttachmentUploadRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getKey())){
            requestBody.setKey(CosPathUtils.defaultBuild(requestBody.getFile().getName()));
        }

        PutObjectResult result = qcloudCosService.putObject(null,requestBody.getKey(),requestBody.getFile());

        String url;
        if (null != qcloudCosService.getQcloudCosConfig().getImageDomain() && ContentTypeUtils.isImage(requestBody.getFile())) {
            url = qcloudCosService.getQcloudCosConfig().getImageDomain() + requestBody.getKey();
        } else {
            url = qcloudCosService.getQcloudCosConfig().getDomain() + requestBody.getKey();
        }
        Attachment attachment = Attachment.builder()
                .belongsTo(requestBody.getBelongsTo())
                .relationId(requestBody.getRelationId())
                .name(requestBody.getFile().getName())
                .type(ContentTypeUtils.getExtension(requestBody.getFile().getName()))
                .url(url)
                .size(requestBody.getFile().length())
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

    @Deprecated
    private Response uploadByAliyun(File file, String relationId, String belongsTo){
        String key = keyGenerate(file.getName(),relationId,belongsTo);
        OssFile ossFile=null;
        try {
            ossFile = ossService.upload(file, key);
        } catch (Exception e) {
            ApiAssert.state(false, SystemCode.Exception.REMOTE_REQUEST_FAILURE, e.getMessage());
        }

        //附件记录
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
        return Response.success(Lists.newArrayList(attachment));
    }

    private String keyGenerate(String fileName, String relationId, String belongsTo){
        return String.format("%s/%s/%s",relationId,belongsTo, PathBuilder.uuidNameBuild(fileName));
    }

}
