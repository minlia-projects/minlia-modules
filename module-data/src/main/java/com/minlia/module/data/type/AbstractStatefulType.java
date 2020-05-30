package com.minlia.module.data.type;

import com.minlia.module.data.enumeration.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractStatefulType extends AbstractIdentifableType implements StatefulType {

    private DataStatusEnum dataStatus;

}
