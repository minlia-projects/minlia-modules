package com.minlia.module.attachment.v1.repository;


import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.module.attachment.v1.domain.Attachment;

/**
 * Created by will on 6/21/17.
 */
public interface AttachmentRepository extends AbstractRepository<Attachment,Long> {

    Attachment findByAccessToken(String accessToken);

}
