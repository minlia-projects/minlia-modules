package com.minlia.modules.aliyun.oss.api.body;

import com.minlia.modules.aliyun.oss.api.enumeration.MtsTemplateType;
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

    private MtsTemplateType mtsTemplateType;

}
