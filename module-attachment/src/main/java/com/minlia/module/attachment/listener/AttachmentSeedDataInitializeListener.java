package com.minlia.module.attachment.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AttachmentSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        log.debug("Starting Initialize Attachment Seed Data ");
    }

}