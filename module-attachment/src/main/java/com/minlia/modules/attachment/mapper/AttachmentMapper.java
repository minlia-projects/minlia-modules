package com.minlia.modules.attachment.mapper;


import com.minlia.modules.attachment.bean.AttachmentQO;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by garen on 2017/7/27.
 */
@Component
public interface AttachmentMapper {

    void create(Attachment attachment);

    void createBatch(List<Attachment> attachments);

    void update(Attachment attachment);

    void delete(Long id);

    void deleteByRelationIdAndBelongsTo(String relationId, String belongsTo);

    Attachment queryOne(AttachmentQO qo);

    List<Attachment> queryList(AttachmentQO qo);

    Attachment queryFirstByUnusedKey(String key);

    String queryUrls(String relationId, String belongsTo);

    String queryFirstUrl(String relationId, String belongsTo);
}
