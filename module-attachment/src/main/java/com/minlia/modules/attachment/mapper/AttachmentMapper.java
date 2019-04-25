package com.minlia.modules.attachment.mapper;


import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.ro.AttachmentQRO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by garen on 2017/7/27.
 */
@Component
public interface AttachmentMapper {

    int create(Attachment attachment);

    int createBatch(List<Attachment> attachments);

    int update(Attachment attachment);

    int delete(Long id);

    int deleteByRelationIdAndBelongsTo(String relationId, String belongsTo);

    int deleteByRelationIdAndBelongsToAndNotExistAccessKeys(String relationId, String belongsTo, List<String> accessKeys);

    long queryCount(AttachmentQRO qro);

    Attachment queryOne(AttachmentQRO qo);

    List<Attachment> queryList(AttachmentQRO qo);


    List<String> queryUrlList(AttachmentQRO qo);

    Attachment queryFirstByUnusedKey(String key);

    String queryUrls(String relationId, String belongsTo);

    String queryFirstUrl(String relationId, String belongsTo);

}
