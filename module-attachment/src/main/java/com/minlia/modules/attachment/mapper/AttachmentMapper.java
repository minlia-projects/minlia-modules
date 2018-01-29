package com.minlia.modules.attachment.mapper;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.modules.attachment.body.AttachmentQueryRequestBody;
import com.minlia.modules.attachment.entity.Attachment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 6/21/17.
 */
@Component
public interface AttachmentMapper {

    void create(Attachment attachment);

    List<Attachment> createBatch(List<Attachment> attachments);

    void update(Attachment attachment);

    void delete(Long id);

    void deleteByBusinessIdAndBusinessType(String businessId, String businessType);

    List<Attachment> queryByBusinessIdAndBusinessType(String businessId, String businessType);

    Attachment queryById(Long id);

    Attachment queryByAccessKey(String accessKey);

    List<Attachment> queryList(AttachmentQueryRequestBody body);

    PageInfo<Attachment> queryPage(AttachmentQueryRequestBody body, Page page);

}
