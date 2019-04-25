package com.minlia.module.aliyun.dypls.ro;

import com.minlia.module.common.validation.Cellphone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by garen on 2018/5/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindAxnRO {

    private String poolKey;

    /**
     * AXB关系中的A号码
     */
    @NotBlank
    @Cellphone
    private String phoneNoA;

    /**
     * AXB关系中的B号码
     */
    @Cellphone
    private String phoneNoB;

    /**
     * 绑定关系对应的失效时间-不能早于当前系统时间
     */
    @NotNull
    private Date expireTime;

    /**
     * 是否需要录制音频-默认是false
     */
    private Boolean isRecordingEnabled;

    /**
     * 外部业务自定义ID属性
     */
    private String outId;

}
