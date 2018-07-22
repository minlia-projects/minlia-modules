package com.minlia.module.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.data.context.UserPrincipalHolder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(
//        fieldVisibility = JsonAutoDetect.Visibility.DEFAULT,
//        getterVisibility = JsonAutoDetect.Visibility.NONE,
//        setterVisibility = JsonAutoDetect.Visibility.NONE,
//        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
//        creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractEntity extends WithDateEntity {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @CreatedBy
    @NotBlank
    private String createBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    public String getCreateBy() {
        return null == createBy ? UserPrincipalHolder.getCurrentUserLogin() : createBy;
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
    public String getCreateByAsJson() {
        return this.createBy;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedByAsJson() {
        return this.lastModifiedBy;
    }

}