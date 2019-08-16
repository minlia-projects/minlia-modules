/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.minlia.modules.rebecca.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织树
 *
 * @author garen
 * @since 2019/8/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleTree extends TreeNode {

    private String code;

    private String name;

    /**
     * 顺序
     */
    private Integer sort;

}
