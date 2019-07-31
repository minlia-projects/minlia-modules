package com.minlia.module.aliyun.dypls.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * Created by garen on 2018/5/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindAxnQRO extends QueryRequest {

    private String poolKey;

    /**
     * 三元绑定关系对应的绑定ID
     */
    private String subsId;

    /**
     * 调用绑定接口时分配的隐私号码
     */
    private String secretNo;

    /**
     * AXB关系中的A号码
     */
    private String phoneNoA;

    /**
     * 绑定关系对应的失效时间-不能早于当前系统时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime expireTime;

    /**
     * 外部业务自定义ID属性
     */
    private String outId;

}
