package com.minlia.module.attachment.v1.service.impl;


import com.minlia.module.attachment.v1.domain.Attachment;
import com.minlia.module.attachment.v1.repository.AttachmentRepository;
import com.minlia.module.attachment.v1.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository repository;


    @Override
    public void bindByAccessToken(String accessToken,String businessId) {
        Attachment attachment = repository.findByAccessToken(accessToken);
        attachment.setBusinessId(businessId);
        repository.save(attachment);
    }
}
