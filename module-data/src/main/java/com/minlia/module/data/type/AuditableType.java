package com.minlia.module.data.type;


import java.time.LocalDateTime;

/**
 * 带审计的实体
 *
 * 关于日期类型的使用, 请参考: https://www.baeldung.com/migrating-to-java-8-date-time-api
 *
 */
public interface AuditableType extends IdentifableType {

    public String getCreatedBy();

    public String getLastModifiedBy();

    public LocalDateTime getCreatedDate();

    public LocalDateTime getLastModifiedDate();

}
