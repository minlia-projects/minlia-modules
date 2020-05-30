package com.minlia.module.data.type;

import javax.sql.rowset.serial.SerialBlob;
import java.io.Serializable;

/**
 * 可识别的实体
 */
public interface IdentifableType extends Serializable {

    Long getId();

}
