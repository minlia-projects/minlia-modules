package com.minlia.modules.rbac.menu.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.modules.rbac.enumeration.NavigationType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "导航-查询")
public class MenuQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private Date createDate;

    private Date lastModifiedDate;

    private boolean isOneLevel;

    private Long roleId;

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
    private String text;

    /**
     * 国际化key
     */
    private String i18n;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由
     */
    private String link;

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

}
