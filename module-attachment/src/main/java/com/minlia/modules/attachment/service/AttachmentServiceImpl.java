package com.minlia.modules.attachment.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.modules.attachment.body.AttachmentCreateRequestBody;
import com.minlia.modules.attachment.body.AttachmentData;
import com.minlia.modules.attachment.body.AttachmentQueryRequestBody;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.mapper.AttachmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public List<Attachment> create(List<Attachment> attachments) {
        return attachmentMapper.createBatch(attachments);
    }

    @Override
    public List<Attachment> create(AttachmentCreateRequestBody body) {
        attachmentMapper.deleteByBusinessIdAndBusinessType(body.getBusinessId(),body.getBusinessType());

        List<Attachment> attachments = Lists.newArrayList();
        for (AttachmentData data : body.getData()) {
            Attachment attachment = Attachment.builder().businessId(body.getBusinessId()).businessType(body.getBusinessType()).url(data.getUrl()).accessKey(data.getKey()).build();
            attachmentMapper.create(attachment);
            attachments.add(attachment);
        }
        return attachments;
    }

    @Override
    public StatefulBody update(Attachment entity) {
        return null;
    }

    @Override
    public void bindByAccessKey(String accessKey,String businessId, String businessType) {
        Attachment attachment = attachmentMapper.queryByAccessKey(accessKey);
        attachment.setBusinessId(businessId);
        attachment.setBusinessType(businessType);
        attachmentMapper.update(attachment);
    }

    @Override
    public StatefulBody delete(Long id) {
        attachmentMapper.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @Override
    public StatefulBody delete(String businessId, String businessType) {
        attachmentMapper.deleteByBusinessIdAndBusinessType(businessId,businessType);
        return SuccessResponseBody.builder().build();
    }

    @Override
    public Attachment queryById(Long id) {
        return attachmentMapper.queryById(id);
    }

    @Override
    public Attachment queryByAccessKey(String accessKey) {
        return attachmentMapper.queryByAccessKey(accessKey);
    }

    @Override
    public List<Attachment> queryAllByBusinessIdAndBusinessType(String businessId, String businessType) {
        return attachmentMapper.queryByBusinessIdAndBusinessType(businessId,businessType);
    }

    @Override
    public List<Attachment> queryList(AttachmentQueryRequestBody body) {
        return attachmentMapper.queryList(body);
    }

    @Override
    public PageInfo<Attachment> queryPage(AttachmentQueryRequestBody body, Page page) {
        return attachmentMapper.queryPage(body,page);
    }

}
