package com.minlia.module.approved.email.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRichtextRO extends EmailBaseRO {

    @NotBlank
    private String templateCode;

    private Map variables;

}
