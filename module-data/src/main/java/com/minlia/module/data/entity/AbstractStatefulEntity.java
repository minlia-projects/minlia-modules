package com.minlia.module.data.entity;

import com.minlia.module.data.enumeration.DataStatusEnum;
import com.minlia.module.data.type.StatefulType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author william raym at minlia.com
 * Created on: 2020-05-30
 */
@Data
@MappedSuperclass
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractStatefulEntity extends AbstractIdentifiableEntity implements StatefulType {
    @Column(name = "data_status",  columnDefinition = "TINYINT DEFAULT 1")
    private DataStatusEnum dataStatus;

}
