package com.minlia.modules.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {

    private String url;

    private String eTag;

    private String fileName;

    private String originalFilename;

    private String contentType;

    private long size;

}