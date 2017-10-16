/*
 * Copyright © 2016 Minlia (http://oss.minlia.com/license/framework/2016)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minlia.cloud.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minlia.cloud.annotation.SearchField;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mybatis.annotations.DynamicSearch;
import org.springframework.data.mybatis.annotations.TypeHandler;

//import com.github.javaplugs.mybatis.LocalDateTimeTypeHandler;
//import com.github.javaplugs.mybatis.ThreetenbpZonedDateTimeTypeHandler;
//import com.github.javaplugs.mybatis.ZonedDateTimeTypeHandler;

//1. 定义实体父类
@MappedSuperclass
// 2. 定义为可审计
@Audited
// 3. 定义审计监听器
//@EntityListeners(value = {AuditingEntityListener.class, AbstractAuditingEntityListener.class})
//@EntityListeners(value = {AbstractAuditingEntityListener.class})

@EntityListeners(AuditingEntityListener.class)
// 4. 定义Json检测特征
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.DEFAULT, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)@DynamicSearch
/**
 * 审计父实体
 * 申明USER为审计人
 * @since 1.0.0
 */
@org.springframework.data.mybatis.annotations.MappedSuperclass
public abstract class AbstractAuditingEntity extends AbstractPersistable<Long> {//implements Persistable{

    /**
     * ID
     */
//, generator = PersistenceConstants.SEQUENCE_GENERATOR_NAME for oracle


    @JsonProperty
    @JsonSerialize(using=ToStringSerializer.class)
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchField
    @JSONField
    protected Long id;


    @Column(name = "enabled", columnDefinition = "tinyint(5) default 1")
//    @Column()
    @JsonIgnore
    @JSONField(serialize = false,deserialize = true)
    private Boolean enabled=Boolean.TRUE;


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }




    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @CreatedDate
//    @Type(type = "org.jadira.usertype.dateandtime.PersistentLocalDateTime")//
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    @TypeHandler(value = LocalDateTimeTypeHandler.class)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
//    @Type(type = "org.jadira.usertype.dateandtime.PersistentLocalDateTime")
    @Column(name = "last_modified_date")
    @JsonIgnore
    @TypeHandler(value = LocalDateTimeTypeHandler.class)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    //    @Transient
//    @org.springframework.data.annotation.Transient
//    @JSONField(serialize = false)
//    private Long createdDateTimestamp;
//
//    @Transient
//    @org.springframework.data.annotation.Transient
//    @JSONField(serialize = false)
//    private Long lastModifiedDateTimestamp;




    @Override
    @Transient
    @JSONField(serialize = false)
    @org.springframework.data.annotation.Transient
    public int hashCode() {
        return 17 + (isEmpty() ? 0 : getId().hashCode() * 31);
    }

    /**
     * 判断是否相等
     *
     * @param obj 对象
     * @return 是否相等
     */
    @Override
    @Transient
    @org.springframework.data.annotation.Transient
    @JSONField(serialize = false)
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (isEmpty() || obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        AbstractAuditingEntity entity = (AbstractAuditingEntity) obj;
        if (entity.isEmpty()) {
            return false;
        }
        return getId().equals(entity.getId());
    }


    public String toString() {
        return "AbstractAuditingEntity (id=" + this.getId() + ")";
    }


    /**
     * 判断是否为空
     *
     * @return 是否为空
     */
    @Transient
    @org.springframework.data.annotation.Transient
    @JSONField(serialize = false)
    public boolean isEmpty() {
       return (null == getId()|| 0==getId());
    }


    @Transient // DATAJPA-622
    @org.springframework.data.annotation.Transient
    public boolean isNew() {
        return (null == getId()|| 0==getId());
    }

}
