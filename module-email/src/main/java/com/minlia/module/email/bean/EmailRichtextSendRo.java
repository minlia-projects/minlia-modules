package com.minlia.module.email.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRichtextSendRo extends EmailSendRo {

    @NotBlank
    private String templateCode;

    private Map variables;

}
