package com.minlia.module.data.type;

import com.minlia.module.data.enumeration.DataStatusEnum;

/**
 * 带状态的实体
 */
public interface StatefulType extends IdentifableType {

    DataStatusEnum getDataStatus();

}
