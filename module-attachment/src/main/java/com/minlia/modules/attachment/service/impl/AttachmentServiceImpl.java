package com.minlia.modules.attachment.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.attachment.constant.AttachmentCode;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.event.AttachmentEvent;
import com.minlia.modules.attachment.mapper.AttachmentMapper;
import com.minlia.modules.attachment.ro.AttachmentCRO;
import com.minlia.modules.attachment.ro.AttachmentQRO;
import com.minlia.modules.attachment.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    public Attachment create(Attachment attachment) {
        attachmentMapper.create(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public List<Attachment> create(List<Attachment> attachments) {
        attachmentMapper.createBatch(attachments);
        return attachments;
    }

    @Override
    @Transactional
    public List<Attachment> create(AttachmentCRO cto) {
        attachmentMapper.deleteByRelationIdAndBelongsTo(cto.getRelationId(), cto.getBelongsTo());

        List<Attachment> attachments = Lists.newArrayList();
        for (AttachmentCRO.Data data : cto.getData()) {
            Attachment attachment = Attachment.builder()
                    .relationId(cto.getRelationId())
                    .belongsTo(cto.getBelongsTo())
                    .url(data.getUrl())
                    .accessKey(data.getAccessKey())
                    .build();
            attachmentMapper.create(attachment);
            attachments.add(attachment);
        }

        AttachmentEvent.onUpload(attachments.get(0));
        return attachments;
    }

    @Override
    @Transactional
    public Attachment update(Attachment attachment) {
        attachmentMapper.update(attachment);
        AttachmentEvent.onUpload(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public String bindByAccessKey(String accessKey, String relationId, String belongsTo) {
        if (StringUtils.isBlank(accessKey)) {
            attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
            return null;
        } else {
            Attachment attachment = attachmentMapper.queryOne(AttachmentQRO.builder().relationId(relationId).belongsTo(belongsTo).accessKey(accessKey).build());
            if (null != attachment) {
                return attachment.getUrl();
            } else {
                attachment = attachmentMapper.queryFirstByUnusedKey(accessKey);
                ApiAssert.notNull(attachment, AttachmentCode.Message.ETAG_NOT_EXISTS);
                attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
                attachment.setRelationId(relationId);
                attachment.setBelongsTo(belongsTo);
                attachmentMapper.update(attachment);
                return attachment.getUrl();
            }
        }
    }

    @Override
    @Transactional
    public void bindByAccessKey(List<String> accessKeys, String relationId, String belongsTo) {
        if (CollectionUtils.isEmpty(accessKeys)) {
            attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
        } else {
            for (String accessKey : accessKeys) {
                long count = attachmentMapper.queryCount(AttachmentQRO.builder().relationId(relationId).belongsTo(belongsTo).accessKey(accessKey).build());
                if (count == 0) {
                    Attachment attachment = attachmentMapper.queryFirstByUnusedKey(accessKey);
                    ApiAssert.notNull(attachment, AttachmentCode.Message.ETAG_NOT_EXISTS);
                    attachment.setRelationId(relationId);
                    attachment.setBelongsTo(belongsTo);
                    attachmentMapper.update(attachment);
                }
            }
            attachmentMapper.deleteByRelationIdAndBelongsToAndNotExistAccessKeys(relationId, belongsTo, accessKeys);
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        attachmentMapper.delete(id);
        return Response.success();
    }

    @Override
    @Transactional
    public Response delete(String relationId, String belongsTo) {
        attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
        return Response.success();
    }

    @Override
    public Attachment queryById(Long id) {
        return attachmentMapper.queryOne(AttachmentQRO.builder().id(id).build());
    }

    @Override
    public Attachment queryOne(AttachmentQRO qo) {
        return attachmentMapper.queryOne(qo);
    }

    @Override
    public List<Attachment> queryList(AttachmentQRO qo) {
        return attachmentMapper.queryList(qo);
    }

    @Override
    public PageInfo<Attachment> queryPage(AttachmentQRO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(() -> attachmentMapper.queryList(qo));
    }

    @Override
    public List<String> queryUrls(AttachmentQRO qo) {
        return attachmentMapper.queryUrlList(qo);
    }

    @Override
    public String queryUrls(String relationId, String belongsTo) {
        return attachmentMapper.queryUrls(relationId, belongsTo);
    }

    @Override
    public String queryFirstUrl(String relationId, String belongsTo) {
        return attachmentMapper.queryFirstUrl(relationId, belongsTo);
    }

}
