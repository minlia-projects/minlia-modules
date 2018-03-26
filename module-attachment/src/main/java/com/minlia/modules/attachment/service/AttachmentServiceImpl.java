package com.minlia.modules.attachment.service;


import com.github.pagehelper.PageHelper;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    @Transactional
    public List<Attachment> create(List<Attachment> attachments) {
        return attachmentMapper.createBatch(attachments);
    }

    @Override
    @Transactional
    public List<Attachment> create(AttachmentCreateRequestBody requestBody) {
        attachmentMapper.deleteByBusinessIdAndBusinessType(requestBody.getBusinessId(),requestBody.getBusinessType());

        List<Attachment> attachments = Lists.newArrayList();
        for (AttachmentData data : requestBody.getData()) {
            Attachment attachment = Attachment.builder().businessId(requestBody.getBusinessId()).businessType(requestBody.getBusinessType()).url(data.getUrl()).accessKey(data.getKey()).build();
            attachmentMapper.create(attachment);
            attachments.add(attachment);
        }
        return attachments;
    }

    @Override
    @Transactional
    public Attachment update(Attachment attachment) {
        attachmentMapper.update(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public void bindByAccessKey(String accessKey,String businessId, String businessType) {
        Attachment attachment = attachmentMapper.queryByAccessKey(accessKey);
        attachment.setBusinessId(businessId);
        attachment.setBusinessType(businessType);
        attachmentMapper.update(attachment);
    }

    @Override
    @Transactional
    public StatefulBody delete(Long id) {
        attachmentMapper.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @Override
    @Transactional
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
    public List<Attachment> queryList(AttachmentQueryRequestBody requestBody) {
        return attachmentMapper.queryList(requestBody);
    }

    @Override
    public PageInfo<Attachment> queryPage(AttachmentQueryRequestBody requestBody, Pageable pageable) {
        return PageHelper.startPage(pageable.getOffset(), pageable.getPageSize()).doSelectPageInfo(()-> attachmentMapper.queryList(requestBody));
    }

}
