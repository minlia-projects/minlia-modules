package com.minlia.modules.attachment.service;


import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.aliyun.oss.api.constant.UploadCode;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.util.CosPathUtils;
import com.minlia.modules.qcloud.oss.service.Qcloud1CosService;
import com.minlia.modules.qcloud.oss.util.QcloudCosUtils;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class AttachmentUploadServiceImpl implements AttachmentUploadService {

    private static List<String> imgageContentTypes = Lists.newArrayList("image/jpeg","image/png");

    @Autowired
    private OssService ossService;

    @Autowired
    private Qcloud1CosService qcloud1CosService;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public StatefulBody upload(MultipartFile file) throws Exception {
        System.out.println(file.getContentType());

        String path = CosPathUtils.defaultBuild(file.getOriginalFilename());
        PutObjectResult result = qcloud1CosService.putObject(null,path,file.getInputStream(), QcloudCosUtils.createDefaultObjectMetadata(file));
        OssFile ossFile= new OssFile(result.getETag());
        ossFile.setContentType(file.getContentType());
        ossFile.setName(file.getOriginalFilename());
        ossFile.setSize(file.getSize());
        if (null != qcloud1CosService.getQcloudCosConfig().getImageDomain() && imgageContentTypes.contains(file.getContentType())) {
            ossFile.setUrl(qcloud1CosService.getQcloudCosConfig().getImageDomain() + path);
        } else {
            ossFile.setUrl(qcloud1CosService.getQcloudCosConfig().getDomain() + path);
        }
        return SuccessResponseBody.builder().message("上传成功").payload(ossFile).build();
    }

    @Override
    public StatefulBody upload(MultipartFile file, String relationId, String belongsTo) throws Exception {
        String path = CosPathUtils.defaultBuild(file.getOriginalFilename());

        PutObjectResult result = qcloud1CosService.putObject(null,path,file.getInputStream(), QcloudCosUtils.createDefaultObjectMetadata(file));
        Attachment attachment = Attachment.builder()
                .belongsTo(belongsTo)
                .relationId(relationId)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .url(qcloud1CosService.getQcloudCosConfig().getDomain() + path)
                .size(file.getSize())
                .accessKey(result.getETag())
                .build();

        //附件记录
        attachmentService.create(attachment);
        return SuccessResponseBody.builder().message("上传成功").payload(Lists.newArrayList(attachment)).build();
    }

    @Override
    public StatefulBody upload(File file, String relationId, String belongsTo) throws Exception {
        //检查是否满足上传条件 TODO 上传数量、belongsTo是否存在

        String key = keyGenerate(file.getName(),relationId,belongsTo);
        OssFile ossFile=null;
        try {
            ossFile = ossService.upload(file, key);
        } catch (Exception e) {
            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
        }

        //附件记录
        Attachment attachment = Attachment.builder().relationId(relationId).belongsTo(belongsTo).name(ossFile.getOriginalName()).type(ossFile.getContentType()).url(ossFile.getUrl()).size(ossFile.getSize()).accessKey(ossFile.geteTag()).build();
        attachmentService.create(attachment);
        return SuccessResponseBody.builder().message("上传成功").payload(Lists.newArrayList(attachment)).build();
    }

    private String keyGenerate(String fileName, String relationId, String belongsTo){
        return String.format("%s/%s/%s",relationId,belongsTo, PathBuilder.uuidNameBuild(fileName));
    }

}
