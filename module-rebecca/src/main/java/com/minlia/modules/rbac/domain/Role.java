package com.minlia.modules.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.minlia.cloud.entity.AbstractEntity;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Data;
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
@Data
public class Role extends AbstractEntity {

    @NotNull
    @JsonProperty
    private String code;

    @JsonProperty
    private String label;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "map_role_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions;


    @JsonIgnore
    @ManyToMany
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
    public void addPermission(Set<Permission> permission) {
        if(null==this.permissions){
            this.permissions=Sets.newHashSet();
        }
        this.permissions.addAll(permission);
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


}
