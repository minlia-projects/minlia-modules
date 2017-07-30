package com.minlia.cloud.data.batis.support.persistence.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.annotation.SearchField;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Id;
import org.springframework.data.mybatis.annotations.MappedSuperclass;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@javax.persistence.MappedSuperclass
public abstract class AbstractEntity extends AbstractDataEntity<Long> {

    private static final long serialVersionUID = 1L;

    @SearchField
    @Id(strategy = Id.GenerationType.IDENTITY)
    @Column(name = "id")

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column
    @JsonProperty
    @JSONField(serialize = false)
    protected Long id;

    @PrePersist
    public void preInssert() {
//        if (StringUtils.isEmpty(id)) {
//            setId(IdGen.uuid());
//        }
    }

    //

    @PreUpdate
    public void preUpdate() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (null != id) {
            this.id = id;
        }
    }

    @javax.persistence.Transient
    @Transient
    @JsonIgnore
    public boolean isNew() {
        return (null == id);
    }
}
