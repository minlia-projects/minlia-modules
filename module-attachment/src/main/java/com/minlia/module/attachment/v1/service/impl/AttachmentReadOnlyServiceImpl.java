package com.minlia.module.attachment.v1.service.impl;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.module.attachment.v1.dao.AttachmentDao;
import com.minlia.module.attachment.v1.domain.Attachment;
import com.minlia.module.attachment.v1.service.AttachmentReadOnlyService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 498391498 on 2017/11/8.
 */
public class AttachmentReadOnlyServiceImpl extends AbstractReadOnlyService<AttachmentDao, Attachment, Long> implements AttachmentReadOnlyService {
    @Autowired
    AttachmentDao attachmentDao;
}
