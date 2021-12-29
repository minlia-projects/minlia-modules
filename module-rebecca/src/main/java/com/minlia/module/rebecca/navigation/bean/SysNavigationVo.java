package com.minlia.module.rebecca.navigation.bean;

import com.minlia.module.common.enums.TreeNodeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 我的导航
 *
 * @author garen
 * @date 6/17/17
 */
@Data
public class SysNavigationVo implements Serializable {

    private Long id;

    private TreeNodeTypeEnum type;

    /**
     * 名称
     */
    private String name;

    /**
     * 国际化KEY
     */
    private String i18n;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由
     */
    private String state;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 是否展示
     */
    private Boolean hideFlag;

    private List<SysNavigationVo> children;

}