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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class AttachmentUploadServiceImpl implements AttachmentUploadService {

    @Autowired
    private OssService ossService;
    @Autowired
    private AttachmentService attachmentService;

    @Override
    public StatefulBody upload(MultipartFile file, String relationId, String belongsTo) throws Exception {
        return null;
    }

    @Override
    public StatefulBody upload(File file, String relationId, String belongsTo) throws Exception {
        //检查是否满足上传条件 TODO 上传数量、belongsTo是否存在

        List<Attachment> attachments = Lists.newArrayList();
        String key = keyGenerate(file.getName(),relationId,belongsTo);
        OssFile ossFile=null;
        try {
            ossFile = ossService.upload(file, key);
        } catch (Exception e) {
            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
        }

        //附件记录
        Attachment attachment = Attachment.builder().relationId(relationId).belongsTo(belongsTo).name(ossFile.getOriginalName()).type(ossFile.getContentType()).url(ossFile.getUrl()).size(ossFile.getSize()).accessKey(ossFile.geteTag()).build();
        attachments.add(attachment);
        return  SuccessResponseBody.builder().message("上传成功").payload(attachmentService.create(attachments)).build();
    }

    private String keyGenerate(String fileName, String relationId, String belongsTo){
        return String.format("%s/%s/%s",relationId,belongsTo, PathBuilder.uuidNameBuild(fileName));
    }

}
