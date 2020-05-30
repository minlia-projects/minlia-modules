package com.minlia.module.data.type;

import com.minlia.module.data.enumeration.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author william raym at minlia.com
 * Created on: 2020-05-30
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractStatefulType extends AbstractIdentifiableType implements StatefulType {

    private DataStatusEnum dataStatus;

}
