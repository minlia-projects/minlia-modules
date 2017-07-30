/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.minlia.cloud.data.batis.support.persistence.entity;

import com.minlia.cloud.annotation.SearchField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
@javax.persistence.MappedSuperclass
public abstract class AbstractStatefulEntity<ID extends Serializable> extends AbstractAuditableEntity<ID> {

    private static final long serialVersionUID = 1L;


    @Column(name = "status")
    @SearchField
//    @DictType(name = "sys_status")
    @ApiModelProperty(hidden = true)

    @javax.persistence.Column
    protected Integer status;

    public AbstractStatefulEntity() {
        super();
        this.status = FLAG_NORMAL;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
