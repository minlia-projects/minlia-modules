package com.minlia.module.library.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_library")
@ApiModel(value = "SysLibraryEntity对象", description = "文库")
public class SysLibraryEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty(value = "令牌")
    @TableField("access_key")
    private String accessKey;

    @ApiModelProperty(value = "文件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @TableField("file_type")
    private String fileType;

    @ApiModelProperty(value = "大小")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty(value = "地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}