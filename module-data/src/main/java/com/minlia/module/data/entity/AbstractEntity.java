package com.minlia.module.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.data.context.UserPrincipalHolder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.validation.constraints.NotNull;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(
//        fieldVisibility = JsonAutoDetect.Visibility.DEFAULT,
//        getterVisibility = JsonAutoDetect.Visibility.NONE,
//        setterVisibility = JsonAutoDetect.Visibility.NONE,
//        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
//        creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractEntity extends WithDateEntity {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @NotNull
    private String createBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public String getCreateBy() {
        return UserPrincipalHolder.getCurrentUserLogin();
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastModifiedBy() {
        return UserPrincipalHolder.getCurrentUserLogin();
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonProperty("createBy")
    public String getCreateByJosn(){
        return createBy;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedByJosn(){
        return lastModifiedBy;
    }

}