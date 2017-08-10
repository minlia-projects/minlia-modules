package com.qianyi.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

//import org.springframework.data.mybatis.annotations.Column;

/**
 * Created by qianyi on 2017/8/1.
 */


@Entity
@Table(name = "person")
@Getter
@Setter

//BATIS
@org.springframework.data.mybatis.annotations.Entity(name = "person")
public class Person extends AbstractAuditingEntity {



//    /**
//     * ID
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
////, generator = PersistenceConstants.SEQUENCE_GENERATOR_NAME for oracle
//    @JsonProperty
//    @JsonSerialize(using=ToStringSerializer.class)
//
//
//    //BATIS
//    @org.springframework.data.mybatis.annotations.Id(strategy = org.springframework.data.mybatis.annotations.Id.GenerationType.AUTO)
//    @Column(name = "id")
//    protected Long id;


    @JsonProperty
//    @Column(name = "name")
    private String name;

    @JsonProperty
//    @Column(name = "email")
    private String email;
//
//    @JsonProperty
////    @Column(name = "enabled")
//    private Boolean enabled;

    @JsonProperty
//    @Column(name = "people_id")
    private Long peopleId;

//    @OneToOne
//    @org.springframework.data.mybatis.annotations.OneToOne
//    private Category category;


    @Transient
    @org.springframework.data.annotation.Transient
    @JSONField(serialize = false)
    private List<Person> people;



}
