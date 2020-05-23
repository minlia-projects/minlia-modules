package com.minlia.modules.attachment.ro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by garen on 2018/6/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentUploadBody {

    @NotBlank
    private String file;

    @NotBlank
    private String contentType;

    @NotBlank
    private String originalFilename;


    @JsonIgnore
    private String fileName;

    @JsonIgnore
    private Long size;

    @JsonIgnore
    private ByteArrayInputStream inputStream;

}
