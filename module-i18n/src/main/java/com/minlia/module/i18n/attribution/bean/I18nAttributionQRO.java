package com.minlia.module.i18n.attribution.bean;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I18nAttributionQRO extends QueryRequest {

    private Long id;
    private String createBy;
    private String lastModifiedBy;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    /**
     * 归属code
     */
    private String attributionCode;

    /**
     * 国际化code
     */
    private String i18nCode;
}
