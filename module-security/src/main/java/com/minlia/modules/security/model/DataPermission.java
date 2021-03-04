package com.minlia.modules.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPermission {

    /**
     * 组织ID
     */
    private Long orgId;

    private Integer dpType;

    private String dpScope;

}