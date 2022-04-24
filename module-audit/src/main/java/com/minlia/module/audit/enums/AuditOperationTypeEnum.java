package com.minlia.module.audit.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Garen
 */
@Getter
@AllArgsConstructor
public enum AuditOperationTypeEnum {

    CREATE(0, "CREATE"),

    DELETE(1, "DELETE"),

    UPDATE(2, "DELETE"),

    SELECT(3, "SELECT"),

    LOGIN(4, "LOGIN");

    private Integer value;

    @EnumValue
    private String code;

}