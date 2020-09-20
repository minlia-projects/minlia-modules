package com.minlia.module.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(
//        fieldVisibility = JsonAutoDetect.Visibility.DEFAULT,
//        getterVisibility = JsonAutoDetect.Visibility.NONE,
//        setterVisibility = JsonAutoDetect.Visibility.NONE,
//        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
//        creatorVisibility = JsonAutoDetect.Visibility.NONE)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity extends WithIdEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @CreatedBy
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @CreatedDate
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 最后修改人
     */
    @LastModifiedBy
    @TableField(value = "last_modified_by", fill = FieldFill.UPDATE)
    private Long lastModifiedBy;

    /**
     * 最后修改时间
     */
    @LastModifiedDate
    @TableField("last_modified_date")
    private LocalDateTime lastModifiedDate;

}
