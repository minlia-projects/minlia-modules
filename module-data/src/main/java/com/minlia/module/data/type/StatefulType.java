package com.minlia.module.data.type;

import com.minlia.module.data.enumeration.DataStatusEnum;

/**
 * @author william raym at minlia.com
 * Created on: 2020-05-30
 * With statueful type
 */
public interface StatefulType extends IdentifiableType {

    DataStatusEnum getDataStatus();

}
