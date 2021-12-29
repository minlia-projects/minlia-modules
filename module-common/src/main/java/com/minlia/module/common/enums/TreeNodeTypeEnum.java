package com.minlia.module.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 树节点类型
 *
 * @author garen
 */
@Getter
@AllArgsConstructor
public enum TreeNodeTypeEnum {

    /**
     * 根：表示有子导航
     */
    FOLDER(0, "FOLDER"),

    /**
     * 叶：表示没事父导航
     */
    LEAF(1, "LEAF");

    private Integer value;

    @EnumValue
    private String code;

}