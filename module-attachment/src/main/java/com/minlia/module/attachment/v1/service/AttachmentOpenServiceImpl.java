package com.minlia.module.attachment.v1.service;

import com.minlia.module.attachment.v1.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AttachmentOpenServiceImpl implements AttachmentOpenService {


    @Autowired
    AttachmentRepository attachmentRepository;


}
