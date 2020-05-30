package com.minlia.module.data.entity.v1;

import javax.sql.rowset.serial.SerialBlob;
import java.io.Serializable;

/**
 * 可识别的实体
 */
public interface IdentifableEntity extends Serializable {

    Long getId();

}
