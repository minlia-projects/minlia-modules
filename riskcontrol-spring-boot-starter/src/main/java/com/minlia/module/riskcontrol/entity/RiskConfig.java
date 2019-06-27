package com.minlia.module.riskcontrol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 风控配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mdl_risk_config")
//@Table(name = "mdl_risk_config")
public class RiskConfig {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(nullable = false, unique = true)
//    private String key;
//
//    @Column(nullable = false)
//    private String value;
//
//    @Column(nullable = false)
//    private String detail;

    @Column(nullable = false)
    private LocalDateTime time;

}
