package com.minlia.module.data.entity.v1;

import com.minlia.module.data.enumeration.DataStatusEnum;
import com.minlia.module.data.type.AbstractAuditableType;
import com.minlia.module.data.type.AuditableType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AbstractAuditableEntity extends AbstractStatefulEntity implements AuditableType {

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @PreUpdate
    private void preUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    @PrePersist
    private void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

//    @PreRemove
//    protected void preRemove() {
//        updateTime = new Date();
//    }

}
