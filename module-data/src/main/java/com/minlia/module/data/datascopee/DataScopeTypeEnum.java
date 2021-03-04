/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.minlia.module.data.datascopee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum {

    /**
     * 查询全部数据
     */
    ALL(0, "全部"),

    /**
     * 自定义
     */
    CUSTOMIZE(1, "自定义"),

    /**
     * 本级及子级
     */
    OWN_CHILD(2, "本级及子级"),

    /**
     * 本级
     */
    OWN(3, "本级"),

    /**
     * 本级及上级
     */
    OWN_PARENT(4, "本级及上级");

    /**
     * 类型
     */
    private final int type;

    /**
     * 描述
     */
    private final String description;

}
