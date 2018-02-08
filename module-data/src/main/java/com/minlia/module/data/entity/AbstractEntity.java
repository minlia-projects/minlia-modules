package com.minlia.module.data.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import java.util.Date;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(
//        fieldVisibility = JsonAutoDetect.Visibility.DEFAULT,
//        getterVisibility = JsonAutoDetect.Visibility.NONE,
//        setterVisibility = JsonAutoDetect.Visibility.NONE,
//        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
//        creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractEntity extends WithIdEntity {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @NotNull
    private String createBy;

    @CreatedDate
    @NotNull
    private Date createDate = new Date();

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Date lastModifiedDate = new Date();

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}