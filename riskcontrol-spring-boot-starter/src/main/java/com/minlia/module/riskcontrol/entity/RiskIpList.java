package com.minlia.module.riskcontrol.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 风控IP集合
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_risk_ip_list")
@Table(name = "sys_risk_ip_list", uniqueConstraints = {@UniqueConstraint(columnNames = {"country", "start"})})
public class RiskIpList {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = javax.persistence.EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '类型'")
    private RiskTypeEnum type;

    @Enumerated(value = javax.persistence.EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '国家'")
    private EnumCountry country;

    @Column(nullable = false, columnDefinition = "BIGINT COMMENT '开始段'")
    private Long start;

    @Column(nullable = false, columnDefinition = "BIGINT COMMENT '结束段'")
    private Long end;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime time;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0 COMMENT '禁用标识'")
    private Boolean disFlag;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '详情'")
    private String detail;

    /**
     * 国家枚举
     */
    public static enum EnumCountry {
        CN("China"),
        HK("Hong Kong"),
        MO("Macao");

        private String value;

        EnumCountry(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
