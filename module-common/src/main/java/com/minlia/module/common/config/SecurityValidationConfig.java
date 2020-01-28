package com.minlia.module.common.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author garen
 * @version 1.0
 * @description 系统配置
 * @date 2019/6/17 11:47 AM
 */
@ConfigAutowired(type = "BMP_APPLICATION_CONFIG")
@Component
@Data
public class SecurityValidationConfig {

    /**
     * 申请最低贷款金额
     */
    @NotNull
    private BigDecimal applicationMininumLoanAmount;

}