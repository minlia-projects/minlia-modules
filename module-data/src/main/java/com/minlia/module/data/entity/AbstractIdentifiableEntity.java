//package com.minlia.module.data.entity;
//
//import com.minlia.module.data.type.AbstractIdentifiableType;
//import com.minlia.module.data.type.IdentifiableType;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//
///**
// * @author william raym at minlia.com
// * Created on: 2020-05-30
// */
//@Data
//@MappedSuperclass
//@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
//public abstract class AbstractIdentifiableEntity extends AbstractIdentifiableType implements IdentifiableType {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "defaultIdGenerator")
//    @GenericGenerator(name = "defaultIdGenerator", strategy = "com.minlia.module.data.generator.DefaultIdentityGenerator")
//    private Long id;
//
//}
