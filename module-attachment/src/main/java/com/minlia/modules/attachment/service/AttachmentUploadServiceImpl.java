package com.minlia.modules.attachment.service;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.bean.AttachmentUploadTO;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.enumeration.AttachmentUploadTypeEnum;
import com.minlia.modules.attachment.property.AttachmentProperties;
import com.minlia.modules.attachment.util.ContentTypeUtils;
import com.minlia.modules.attachment.util.OSSPathUtils;
import com.minlia.modules.qcloud.oss.service.QcloudCosService;
import com.minlia.modules.qcloud.oss.util.QcloudCosUtils;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        if (AttachmentUploadTypeEnum.aliyun.equals(attachmentProperties.getType())) {
            return uploadByAliyun(file, null, null);
        } else {
            return uploadByQcloud(file, null, null);
        }
    }

    @Override
    public Response upload(MultipartFile file, String relationId, String belongsTo) throws Exception {
        if (AttachmentUploadTypeEnum.aliyun.equals(attachmentProperties.getType())) {
            return uploadByAliyun(file, relationId, belongsTo);
        } else {
            return uploadByQcloud(file, relationId, belongsTo);
        }
    }

    private Response uploadByQcloud(MultipartFile file, String relationId, String belongsTo) throws Exception {
        String path = this.keyGenerate(file, relationId, belongsTo);
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

        String key = keyGenerate(file, relationId, belongsTo);
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

    private String keyGenerate(MultipartFile file, String relationId, String belongsTo){
        if (StringUtils.isNotBlank(belongsTo)) {
            return String.format("%s/%s/%s", relationId, belongsTo, OSSPathUtils.uuidNameBuild(file.getOriginalFilename()));
        } else {
            return OSSPathUtils.defaultBuild(file.getOriginalFilename());
        }
    }

    @Override
    public Response upload(AttachmentUploadTO to) {
        if (StringUtils.isEmpty(to.getKey())){
            to.setKey(OSSPathUtils.defaultBuild(to.getFile().getName()));
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
