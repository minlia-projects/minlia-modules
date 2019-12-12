package com.minlia.modules.attachment.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by garen on 2018/6/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentBase64UploadRO {

    @NotBlank
    private String file;

    @NotBlank
    private String contentType;

    @NotBlank
    private String originalFilename;

//    @JsonIgnore
//    private String fileName;
//
//    @JsonIgnore
//    private Long size;
//
//    @JsonIgnore
//    private ByteArrayInputStream inputStream;

}
