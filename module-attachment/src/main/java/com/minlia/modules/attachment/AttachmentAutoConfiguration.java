package com.minlia.modules.attachment;

import com.minlia.modules.attachment.property.AttachmentProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AttachmentProperties.class})
public class AttachmentAutoConfiguration {

}
