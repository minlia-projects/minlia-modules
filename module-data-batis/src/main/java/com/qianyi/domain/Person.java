package com.qianyi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends AbstractAuditingEntity {


    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty
    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany
    private Set<Cellphone> cellphone;


    @ManyToMany
    @JoinTable(name = "map_team_people",
            inverseJoinColumns = {@javax.persistence.JoinColumn(name = "team_id", referencedColumnName = "id")},
            joinColumns = {@javax.persistence.JoinColumn(name = "person_id", referencedColumnName = "id")})
    private Set<Team> team;

    @OneToOne
    private Iphone iphone;


}
