package com.minlia.module.advertisement.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 链接类型
 *
 * @author garen
 */
@Getter
@AllArgsConstructor
public enum SysAdvertisementTypeEnum {

    PAGE(0, "zh_CN"),
    URL(1, "en_US");

    private Integer value;

    @EnumValue
    private String code;

}
