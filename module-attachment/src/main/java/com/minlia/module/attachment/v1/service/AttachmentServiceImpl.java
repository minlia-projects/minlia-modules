package com.minlia.module.attachment.v1.service;

import com.minlia.boot.v1.service.AbstractService;
import com.minlia.module.attachment.v1.domain.Attachment;
import com.minlia.module.attachment.v1.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AttachmentServiceImpl extends AbstractService<Attachment> implements AttachmentService {

    @Autowired
    private AttachmentRepository repository;

    @Override
    protected JpaRepository<Attachment, Long> getRepository() {
        return repository;
    }

    @Override
    public void bindByAccessToken(String accessToken, String businessId) {
        Attachment attachment = repository.findByAccessToken(accessToken);
        attachment.setBusinessId(businessId);
        repository.save(attachment);
    }
}
