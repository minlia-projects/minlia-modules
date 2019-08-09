package com.minlia.modules.rebecca.bean.vo;

import com.minlia.modules.rebecca.enumeration.NavigationType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 我的导航
 * Created by garen on 6/17/17.
 */
@Data
public class MyNavigationVO implements Serializable {

    private Long id;

    private NavigationType type;

    /**
     * 名称
     */
    private String name;

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
    private Integer orders;

    /**
     * 是否展示
     */
    private Boolean display;

    private List<MyNavigationVO> children;

}
