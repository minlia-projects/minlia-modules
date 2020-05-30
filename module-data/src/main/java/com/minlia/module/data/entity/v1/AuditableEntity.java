package com.minlia.module.data.entity.v1;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 带审计的实体
 *
 * 关于日期类型的使用, 请参考: https://www.baeldung.com/migrating-to-java-8-date-time-api
 *
 */
public interface AuditableEntity extends IdentifableEntity {

    public String getCreatedBy();

    public String getLastModifiedBy();

    public LocalDateTime getCreatedDate();

    public LocalDateTime getLastModifiedDate();

}
