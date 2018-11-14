package com.minlia.modules.attachment.mapper;


import com.minlia.modules.attachment.bean.AttachmentQO;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 6/21/17.
 */
@Component
public interface AttachmentMapper {

    void create(Attachment attachment);

    void createBatch(List<Attachment> attachments);

    void update(Attachment attachment);

    void delete(Long id);

    void deleteByRelationIdAndBelongsTo(String relationId, String belongsTo);

    Attachment queryById(Long id);

    Attachment queryByKey(String key);

    List<Attachment> queryByRelationIdAndBelongsTo(String relationId, String belongsTo);

    List<Attachment> queryList(AttachmentQO body);

    String queryUrls(String relationId, String belongsTo);

    String queryFirstUrl(String relationId, String belongsTo);
}
