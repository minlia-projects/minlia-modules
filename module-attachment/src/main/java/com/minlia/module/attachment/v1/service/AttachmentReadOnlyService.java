package com.minlia.module.attachment.v1.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.attachment.v1.dao.AttachmentDao;
import com.minlia.module.attachment.v1.domain.Attachment;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by henry on 20/10/2017.
 * badcop@163.com
 */
@Transactional(readOnly = true)
public interface AttachmentReadOnlyService extends ReadOnlyService<AttachmentDao, Attachment, Long> {



}