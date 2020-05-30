package com.minlia.module.data.entity.v1;

import com.minlia.module.data.enumeration.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractStatefulEntity extends AbstractIdentifableEntity implements StatefulEntity{

    private DataStatusEnum dataStatus;


    public AbstractStatefulEntity(Long id,DataStatusEnum dataStatus){
        super(id);
        this.dataStatus=dataStatus;
    }
}
