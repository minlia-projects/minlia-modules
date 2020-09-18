package com.minlia.module.attachment.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author garen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {

    private String url;

    private String eTag;

    private String fileName;

    private String originalFilename;

    private String contentType;

    private Long size;

}