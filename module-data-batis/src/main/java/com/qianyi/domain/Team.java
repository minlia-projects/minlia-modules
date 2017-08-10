package com.qianyi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

//import org.springframework.data.mybatis.annotations.Column;

/**
 * Created by qianyi on 2017/8/1.
 */


@Entity
@Table(name = "team")
@Getter
@Setter

public class Team extends AbstractAuditingEntity {


    @JsonProperty
    private String name;

    @ManyToMany
    @JoinTable(name = "map_team_people",
            joinColumns = {@javax.persistence.JoinColumn(name = "team_id", referencedColumnName = "id")},
            inverseJoinColumns = {@javax.persistence.JoinColumn(name = "people_id", referencedColumnName = "id")})
    private Set<Person> people;





}
