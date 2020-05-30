package com.minlia.module.data.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

/**
 * @author william raym at minlia.com
 * Created on: 2020-05-30
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractAuditableType extends AbstractStatefulType implements AuditableType{

    private String createdBy;

    private String lastModifiedBy;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;


}
