package com.minlia.module.article.entity;

import com.minlia.module.article.bean.vo.ArticleSimpleVO;
import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 文章类目
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_article_category")
@ApiModel(value = "SysArticleCategoryEntity对象", description = "文章类目")
public class SysArticleCategoryEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "叶")
    @TableField("is_leaf")
    private Boolean isLeaf;

    @ApiModelProperty(value = "父级类目")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "语言环境")
    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;


    /**
     * 文章子项
     */
    @TableField(exist = false)
    List<ArticleSimpleVO> articles;

    @TableField(exist = false)
    List<SysArticleCategoryEntity> children;

}