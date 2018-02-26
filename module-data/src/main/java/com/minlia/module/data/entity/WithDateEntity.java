package com.minlia.module.data.entity;

import org.springframework.data.annotation.CreatedDate;
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
public abstract class WithDateEntity extends WithIdEntity {

    @CreatedDate
    @NotNull
    private Date createDate = new Date();

    @LastModifiedDate
    private Date lastModifiedDate = new Date();

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}