package com.minlia.modules.rbac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.minlia.cloud.entity.AbstractEntity;
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

    @ManyToMany
    @JoinTable(name = "map_role_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Permission> permissions = Sets.newHashSet();



    @ManyToMany(mappedBy = "roles")
//    @JoinTable(name = "map_users_roles",inverseJoinColumns  = @JoinColumn(name = "user_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
//    @NotAudited
    private Set<User> users;

}
