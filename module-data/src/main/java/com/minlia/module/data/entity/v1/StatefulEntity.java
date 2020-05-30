package com.minlia.module.data.entity.v1;

import com.minlia.module.data.enumeration.DataStatusEnum;

import java.io.Serializable;

/**
 * 带状态的实体
 */
public interface StatefulEntity extends IdentifableEntity {

    DataStatusEnum getDataStatus();

}
