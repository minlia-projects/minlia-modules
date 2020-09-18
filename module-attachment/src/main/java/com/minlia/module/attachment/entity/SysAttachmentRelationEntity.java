package com.minlia.module.attachment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.WithIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 附件-关系
 * </p>
 *
 * @author garen
 * @since 2020-09-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_attachment_relation")
@ApiModel(value = "SysAttachmentRelationEntity对象", description = "附件-关系")
public class SysAttachmentRelationEntity extends WithIdEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    @TableField("relation_to")
    private String relationTo;

    @ApiModelProperty(value = "业务ID")
    @TableField("relation_id")
    private Long relationId;

    @ApiModelProperty(value = "地址")
    @TableField("url")
    private String url;

}
