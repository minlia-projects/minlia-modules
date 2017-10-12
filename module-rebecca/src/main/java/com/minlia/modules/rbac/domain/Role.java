package com.minlia.modules.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.minlia.cloud.entity.AbstractEntity;
import javax.persistence.FetchType;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by will on 8/14/17.
 */
@Entity
public class Role extends AbstractEntity {

    @NotNull
    @JsonProperty
    private String code;

    @JsonProperty
    private String label;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(value = "id")
    @JsonIgnore
    @JoinTable(name = "map_role_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions;


    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(value = "id")
    @JsonIgnore
    @JoinTable(name = "map_user_roles",inverseJoinColumns  = @JoinColumn(name = "user_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<User> users;



    @Transient
    public void addPermission(Permission permission) {
        if(null==this.permissions){
            this.permissions=Sets.newHashSet();
        }
        this.permissions.add(permission);
    }

    @Transient
    public void addPermission(Set<Permission> permissions) {
        if(null==this.permissions){
            this.permissions=Sets.newHashSet();
        }
        this.permissions.addAll(permissions);
    }



//    public void addNavigation(Navigation navigation) {
//        if(null==this.navigations){
//            this.navigations=Sets.newHashSet();
//        }
//        this.navigations.add(navigation);
//    }
//
//    public void addNavigations(Set<Navigation> navigations) {
//        if(null==this.navigations){
//            this.navigations=Sets.newHashSet();
//        }
//        this.navigations.addAll(navigations);
//    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
