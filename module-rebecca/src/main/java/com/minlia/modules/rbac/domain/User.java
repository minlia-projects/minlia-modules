package com.minlia.modules.rbac.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.annotation.SearchField;
import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.modules.security.authentication.credential.*;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by will on 8/14/17.
 * TODO 不能使用Builder 会导致 Data Batis 出现 No TypeHander的错误 在Batis的扫描类中找到以UserBuilder开头的属性给排除掉
 *
 */
@Data
@Entity
public class User extends AbstractEntity implements WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {
    /**
     * 用户名
     */
    @Column(unique = true)
    @SearchField
    private String username;

    /**
     * 手机号码
     */
    @Column(unique = true)
    @SearchField
    private String cellphone;

    /**
     * 邮箱
     */
    @Column(unique = true)
    @SearchField
    private String email;

    /**
     * Global User Identity
     */
    @SearchField
    private String guid;

    /**
     * 密码
     */
    private String password;
    @SearchField
    @Column(name = "first_name")
    private String firstName;
    @SearchField
    private String lastName;
    @SearchField
    private String fullName;

    private String nickName;

    @ManyToMany
    @JoinTable(name = "map_users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles;

    @NotNull
    private Boolean credentialsExpired = false;

    @NotNull
    private Boolean accountExpired = false;

    @NotNull
    private Boolean expired = false;

    @NotNull
    private Boolean locked = false;


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
}
