package com.qianyi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.springframework.data.mybatis.annotations.Column;

/**
 * Created by qianyi on 2017/8/1.
 */


@Entity
@Table(name = "cellphone")
@Getter
@Setter

public class Cellphone extends AbstractAuditingEntity {


    @JsonProperty
    private String number;



    @ManyToOne(targetEntity = Person.class)
//    @JoinColumn(name = "person_id")
    private Person person;


//    @Size(max = 32)
//    @javax.persistence.Column(name = "person_id")
//    private Long personId;
//
//    @ManyToOne
//    @JoinColumn(name = "cellphone_id", insertable = false, updatable = false)
//    private Person person;


}
