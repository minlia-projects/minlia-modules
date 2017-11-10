package com.minlia.modules.starter.oss.v2.api.body;

import com.minlia.modules.starter.oss.v2.api.enumeration.MtsTemplateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtsResponseBody {

    private String requestId;

    private Boolean success;

    private String inputObject;
    private String inputObjectUrl;

    private String outputObject;
    private String outputObjectUrl;

    private MtsTemplateTypeEnum mtsTemplateType;

}
