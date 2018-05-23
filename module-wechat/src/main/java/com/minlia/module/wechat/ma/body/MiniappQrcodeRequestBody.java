package com.minlia.module.wechat.ma.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniappQrcodeRequestBody {

    @NotBlank
    private String type;

    @NotBlank
    private String scene;

    private String path;

    /**
     * 是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
     */
    private Boolean isHyaline;

    private Integer width;

    private Boolean autoColor;

    private Object lineColor;

}
