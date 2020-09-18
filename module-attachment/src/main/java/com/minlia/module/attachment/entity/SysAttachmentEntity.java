package com.minlia.module.attachment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_attachment")
@ApiModel(value = "SysAttachmentEntity对象", description = "附件")
public class SysAttachmentEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    @TableField("relation_to")
    private String relationTo;

    @ApiModelProperty(value = "业务ID")
    @TableField("relation_id")
    private String relationId;

    @ApiModelProperty(value = "令牌")
    @TableField("access_key")
    private String accessKey;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "大小")
    @TableField("size")
    private Long size;

}
