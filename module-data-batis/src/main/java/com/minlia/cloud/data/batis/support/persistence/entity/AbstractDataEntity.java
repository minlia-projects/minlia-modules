package com.minlia.cloud.data.batis.support.persistence.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Version;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.MappedSuperclass;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@javax.persistence.MappedSuperclass
@Data
public abstract class AbstractDataEntity<ID extends Serializable> extends AbstractStatefulEntity<ID> {

    private static final long serialVersionUID = 1L;


    //    @CreatedBy
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    @Column(name = "created_by")


    @javax.persistence.Column
    protected String createdBy;

//    @ManyToOne
//    @Persistent
//    @JoinColumn(name = "created_by")
//    protected SystemUser creator;

    //    @CreatedDate
    @Column(name = "created_date")
    @javax.persistence.Column
    protected Date createdDate = new Date();

    //    @LastModifiedBy
    @Column(name = "last_modified_by")
    @javax.persistence.Column
    protected String lastModifiedBy;

//    @ManyToOne
//    @JoinColumn(name = "last_modified_by")
//    protected SystemUser modifier;
//
//    @LastModifiedDate
//    @Column(name = "last_modified_date")
//    protected Date lastModifiedDate = PublicUtil.getCurrentDate();

    /*** 默认0，必填，离线乐观锁 */
    @Version
    @JSONField(serialize = false)
    @XmlTransient
    @ApiModelProperty(hidden = true)
    @Column(name = "version")
    @javax.persistence.Column
    protected Integer version = 0;

    /*** 备注 */
    @XmlTransient
    @Column(name = "description")
    @javax.persistence.Column
    protected String description;


}
