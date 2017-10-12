package com.minlia.modules.rbac.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.annotation.SearchField;
import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.modules.security.authentication.credential.*;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.*;

/**
 * Created by will on 8/14/17.
 * TODO 不能使用Builder 会导致 Data Batis 出现 No TypeHander的错误 在Batis的扫描类中找到以UserBuilder开头的属性给排除掉
 *
 */
@Entity
public class User extends AbstractEntity implements WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {
    /**
     * 用户名
     */
    @Column(unique = true)
    @SearchField
    @JsonProperty
    private String username;

    /**
     * 手机号码
     */
    @Column(unique = true)
    @SearchField
    @JsonProperty
    private String cellphone;

    /**
     * 邮箱
     */
    @Column(unique = true)
    @SearchField
    @JsonProperty
    private String email;

    /**
     * Global User Identification
     */
    @SearchField
    @JsonProperty
    private String guid;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    @SearchField
    @JsonProperty
    private String firstName;
    @SearchField
    @JsonProperty
    private String lastName;
    @SearchField
    @JsonProperty
    private String fullName;

    @JsonProperty
    private String nickName;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(value = "id")
    @JsonIgnore
    @JoinTable(name = "map_user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @NotNull
    @Column(columnDefinition = "tinyint(5)")
    private Boolean credentialsExpired;

    @NotNull
    @Column(columnDefinition = "tinyint(5)")
    private Boolean accountExpired;

    @NotNull
    @Column(columnDefinition = "tinyint(5)")
    private Boolean expired;

    @NotNull
    @Column(columnDefinition = "tinyint(5)")
    private Boolean locked;


    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @JsonProperty("password")
    @Transient
    @org.springframework.data.annotation.Transient
    public String getPasswordAsJson(){
        //TODO to mask password for output
        return getPassword();
    }

    /**
     * 重写toString方法
     *
     * @return 字符串
     */
    @Override
    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
        return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
    }
    /**
     * 重写equals方法
     *
     * @param obj 对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!AbstractEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;
        return getId() != null ? getId().equals(other.getId()) : false;
    }
    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

}
