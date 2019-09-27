package com.minlia.modules.attachment.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.attachment.bean.AttachmentCTO;
import com.minlia.modules.attachment.bean.AttachmentData;
import com.minlia.modules.attachment.bean.AttachmentQO;
import com.minlia.modules.attachment.constant.AttachmentCode;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.event.AttachmentEvent;
import com.minlia.modules.attachment.mapper.AttachmentMapper;
import lombok.extern.slf4j.Slf4j;
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
    public List<Attachment> create(AttachmentCTO cto) {
        attachmentMapper.deleteByRelationIdAndBelongsTo(cto.getRelationId(),cto.getBelongsTo());

        List<Attachment> attachments = Lists.newArrayList();
        for (AttachmentData data : cto.getData()) {
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

//    @Override
//    @Transactional
//    public String bindByAccessKey(String accessKey, String relationId, String belongsTo) {
//        Attachment attachment = attachmentMapper.queryFirstByUnusedKey(accessKey);
//        ApiAssert.notNull(attachment, AttachmentCode.Message.ETAG_NOT_EXISTS);
//
//        if (null != attachment.getRelationId() || null != attachment.getBelongsTo()) {
//            if (relationId.equals(attachment.getRelationId()) && belongsTo.equals(attachment.getBelongsTo())) {
//                log.info("附件accessKey重复绑定直接通过：{}-{}", relationId, belongsTo);
//            }
//            ApiAssert.state(false, AttachmentCode.Message.ETAG_ALREADY_BIND);
//        }
//
//        attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
//        attachment.setRelationId(relationId);
//        attachment.setBelongsTo(belongsTo);
//        attachmentMapper.update(attachment);
//        return attachment.getUrl();
//    }

    @Override
    @Transactional
    public String bindByAccessKey(String accessKey, String relationId, String belongsTo) {
        if (StringUtils.isBlank(accessKey)) {
            attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
            return null;
        } else {
            Attachment attachment = attachmentMapper.queryOne(AttachmentQO.builder().relationId(relationId).belongsTo(belongsTo).accessKey(accessKey).build());
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



//    @Override
//    @Transactional
//    public void bindByAccessKey(String accessKey, String relationId, String belongsTo) {
//        Attachment attachment = attachmentMapper.queryFirstByUnusedKey(accessKey);
//        ApiAssert.notNull(attachment, AttachmentCode.Message.ETAG_NOT_EXISTS);
////        ApiAssert.isNull(attachment.getRelationId(), AttachmentCode.Message.ETAG_ALREADY_BIND);
////        ApiAssert.isNull(attachment.getBelongsTo(), AttachmentCode.Message.ETAG_ALREADY_BIND);
//
//        attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
//
//        attachment.setRelationId(relationId);
//        attachment.setBelongsTo(belongsTo);
//        attachmentMapper.update(attachment);
//    }
//

    @Override
    @Transactional
    public void bindByAccessKey(List<String> accessKeys, String relationId, String belongsTo) {
        //校验accessKey是否都存在、是否已绑定
        for (String accessKey : accessKeys) {
            Attachment attachment = attachmentMapper.queryFirstByUnusedKey(accessKey);
            ApiAssert.notNull(attachment, AttachmentCode.Message.ETAG_NOT_EXISTS);

            if (null != attachment.getRelationId() || null != attachment.getBelongsTo()) {
                 if (relationId.equals(attachment.getRelationId()) && belongsTo.equals(attachment.getBelongsTo())) {
                     log.info("附件accessKey重复绑定直接通过：{}-{}", relationId, belongsTo);
                     accessKeys.remove(accessKey);
                 }
                 ApiAssert.state(false, AttachmentCode.Message.ETAG_ALREADY_BIND);
            }
        }

        attachmentMapper.deleteByRelationIdAndBelongsTo(relationId, belongsTo);
        for (String accessKey : accessKeys) {
            Attachment attachment = attachmentMapper.queryFirstByUnusedKey(accessKey);
            attachment.setRelationId(relationId);
            attachment.setBelongsTo(belongsTo);
            attachmentMapper.update(attachment);
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
        attachmentMapper.deleteByRelationIdAndBelongsTo(relationId,belongsTo);
        return Response.success();
    }

    @Override
    public Attachment queryById(Long id) {
        return attachmentMapper.queryOne(AttachmentQO.builder().id(id).build());
    }

    @Override
    public Attachment queryOne(AttachmentQO qo) {
        return attachmentMapper.queryOne(qo);
    }

    @Override
    public List<Attachment> queryList(AttachmentQO qo) {
        return attachmentMapper.queryList(qo);
    }

    @Override
    public PageInfo<Attachment> queryPage(AttachmentQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> attachmentMapper.queryList(qo));
    }

    @Override
    public List<String> queryUrls(AttachmentQO qo) {
        return attachmentMapper.queryUrlList(qo);
    }

    @Override
    public String queryUrls(String relationId, String belongsTo) {
        return attachmentMapper.queryUrls(relationId,belongsTo);
    }

    @Override
    public String queryFirstUrl(String relationId, String belongsTo) {
        return attachmentMapper.queryFirstUrl(relationId,belongsTo);
    }

}
