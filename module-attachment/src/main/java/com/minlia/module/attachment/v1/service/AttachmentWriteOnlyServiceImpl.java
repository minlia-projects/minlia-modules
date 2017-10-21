package com.minlia.module.attachment.v1.service;

import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.module.attachment.v1.body.AttachmentQueryRequestBody;
import com.minlia.module.attachment.v1.dao.AttachmentDao;
import com.minlia.module.attachment.v1.domain.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by henry on 20/10/2017.
 * badcop@163.com
 */
@Service
@Slf4j
public class AttachmentWriteOnlyServiceImpl extends
        AbstractWriteOnlyService<AttachmentDao, Attachment, Long> implements
        AttachmentWriteOnlyService {

    @Autowired
    private AttachmentDao attachmentDao;

    @Override
    public void bindByAccessToken(String accessToken, String businessId) {
        Attachment attachment = attachmentDao.findByAccessToken(accessToken);
        attachment.setBusinessId(businessId);
        attachmentDao.save(attachment);
    }

}