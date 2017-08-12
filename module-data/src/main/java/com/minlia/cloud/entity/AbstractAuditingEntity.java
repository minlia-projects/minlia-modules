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
import com.minlia.cloud.entity.listener.AbstractAuditingEntityListener;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mybatis.annotations.DynamicSearch;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

//1. 定义实体父类
@MappedSuperclass
// 2. 定义为可审计
@Audited
// 3. 定义审计监听器
@EntityListeners(value = {AuditingEntityListener.class, AbstractAuditingEntityListener.class})
//@EntityListeners(value = {AbstractAuditingEntityListener.class})
// 4. 定义Json检测特征
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.DEFAULT, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)@DynamicSearch
/**
 * 审计父实体
 * 申明USER为审计人
 * @since 1.0.0
 */
@org.springframework.data.mybatis.annotations.MappedSuperclass
public abstract class AbstractAuditingEntity extends AbstractPersistable<Long> {

    /**
     * ID
     */
//, generator = PersistenceConstants.SEQUENCE_GENERATOR_NAME for oracle


//由父类提供, 不需要了
//    @JsonProperty
//    @JsonSerialize(using=ToStringSerializer.class)
//    @Column(name = "id")
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SearchField
//    @JSONField
//    protected Long id;


    @JsonProperty(value = "id")
    @JSONField(name = "id")
    public Long getIdAsJson(){
        return getId();
    }
    /**
     * 应用系统ID
     */
    private String appId;

    /**
     * is enabled
     */
    @Column(name = "enabled")
    @JsonIgnore
    @JSONField(serialize = false,deserialize = true)
    private Boolean enabled = Boolean.TRUE;


    /**
     * //@Id
     * // MySQL/SQLServer: @GeneratedValue(strategy = GenerationType.AUTO)
     * // Oracle: @GeneratedValue(strategy = GenerationType.AUTO, generator =
     * // "sequenceGenerator")
     *
     * @return
     */
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    // @Enumerated(value = EnumType.ORDINAL)

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    private String createdBy;



    private String lastModifiedBy;



    public String getCreatedBy() {

        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {

        this.createdBy = createdBy;
    }



//    @Temporal(TemporalType.TIMESTAMP)
//    @org.springframework.data.mybatis.annotations.Temporal(value = org.springframework.data.mybatis.annotations.Temporal.TemporalType.TIMESTAMP)
//    protected Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    @org.springframework.data.mybatis.annotations.Temporal(value = org.springframework.data.mybatis.annotations.Temporal.TemporalType.TIMESTAMP)
//    protected Date lastModifiedDate;
//
//
//    public DateTime getCreatedDate() {
//
//        return null == createdDate ? null : new DateTime(createdDate);
//    }
//
//    public void setCreatedDate(final DateTime createdDate) {
//
//        this.createdDate = null == createdDate ? null : createdDate.toDate();
//    }
//
//    public DateTime getLastModifiedDate() {
//
//        return null == lastModifiedDate ? null : new DateTime(lastModifiedDate);
//    }
//
//    public void setLastModifiedDate(final DateTime lastModifiedDate) {
//
//        this.lastModifiedDate = null == lastModifiedDate ? null : lastModifiedDate.toDate();
//    }
//    @JsonProperty(value = "createdDate")
//    public Long createdDateTimestampAsJson() {
//        if (this.getCreatedDate() != null) {
//            createdDateTimestamp = this.getCreatedDate().getMillis();
//        }
//        return createdDateTimestamp;
//    }
//
//    @JsonProperty(value = "lastModifiedDate")
//    public Long getDateModifiedAsJson() {
//        if (this.getLastModifiedDate() != null) {
//            lastModifiedDateTimestamp = this.getLastModifiedDate().getMillis();
//        }
//        return lastModifiedDateTimestamp;
//    }





    public String getLastModifiedBy() {

        return lastModifiedBy;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
    }
    @Transient
    @org.springframework.data.annotation.Transient
    @JSONField(serialize = false)
    private Long createdDateTimestamp;

    @Transient
    @org.springframework.data.annotation.Transient
    @JSONField(serialize = false)
    private Long lastModifiedDateTimestamp;




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
        return getId() == null;
    }

}
