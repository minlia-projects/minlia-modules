package com.minlia.module.riskcontrol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 黑名单，白名单，可疑名单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mdl_black_list")
@Table(name = "mdl_black_list", uniqueConstraints = {@UniqueConstraint(columnNames={"dimension","value"})})
public class BlackList {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 类型
     */
    @Column(nullable = false)
    private EnumType type;

    /**
     * 维度
     */
    @Column(nullable = false)
    private EnumDimension dimension;

    /**
     * 值
     */
    @Column(nullable = false)
    private String value;

    /**
     * 时间
     */
    @Column(insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    private LocalDateTime time;

    /**
     * 详情
     */
    @Column
    private String detail;

    /**
     * 维度枚举
     */
    public static enum EnumDimension {
        MOBILE,
        IP,
        DEVICEID;
    }

    /**
     * 类型枚举
     */
    public static enum EnumType {
        BLACK,
        WHITE,
        GRAY;
    }

}
