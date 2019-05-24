package com.minlia.modules.rbac.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.modules.rbac.enumeration.NavigationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends AbstractEntity {

    /**
    * 父ID
    */
    private Long parentId;

    /**
    * 类型
    */
    private NavigationType type;

    /**
    * 名称
    */
    @Size(max = 50)
    private String text;

    /**
    * 国际化key
    */
    @Size(max = 100)
    private String i18n;

    /**
    * 图标
    */
    @Size(max = 200)
    private String icon;

    /**
    * 路由
    */
    @Size(max = 200)
    private String link;

    /**
    * 排序值
    */
    private Byte sort;

    /**
    * 复用标识
    */
    private Boolean reuseFlag;

    /**
    * 快捷导航标识
    */
    private Boolean shortcutFlag;

    /**
    * 隐藏标识
    */
    private Boolean hideFlag;

    /**
    * 隐藏面包线标识
    */
    private Boolean hideInBreadcrumbFlag;

    /**
    * 禁用标识
    */
    private Boolean disFlag;

    @JsonIgnoreProperties({"id", "createBy", "lastModifiedBy", "createDate", "lastModifiedDate"})
    private List<Menu> children;

}