package com.minlia.module.data.type;


import java.time.LocalDateTime;

/**
 * @author william raym at minlia.com
 * Created on: 2020-05-30
 * With auditable type
 * <p>
 * For further please refer to: https://www.baeldung.com/migrating-to-java-8-date-time-api
 * </p>
 */
public interface AuditableType extends IdentifiableType {

    public String getCreatedBy();

    public String getLastModifiedBy();

    public LocalDateTime getCreatedDate();

    public LocalDateTime getLastModifiedDate();

}
