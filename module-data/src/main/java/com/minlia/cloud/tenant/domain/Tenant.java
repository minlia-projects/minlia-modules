//package com.minlia.cloud.tenant.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import com.minlia.cloud.data.support.constant.PersistenceConstants;
//import com.minlia.cloud.entity.AbstractLocalizedEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Entity;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
///**
// * Created by will on 6/22/17.
// */
//
//// Is those properties publish to json
//@JsonIgnoreProperties(value = {})
//// A JPA Annotation to define as a entity
////@Entity
//// Json sort order
//@JsonPropertyOrder({})
//// A JPA annotation to define as data table name, it will convert to camel case (eg. hello_word) when multiple words
////@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Tenant"
//// ,
//// uniqueConstraints={@UniqueConstraint(columnNames={ })}
////)
//// A JPA annotation to define how to generate sequence
////@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "Tenant")
//// A Lombok annotation to generate Getter and Setter on compilation
//@Data
//// A Lombok annotation to generate ${className}.Builder inner class as a helper class on compilation
//@Builder
//// A Lombok annotation to generate toString method on compilation
////@ToString(exclude = {"roles"})
//// A Lombok annotation to generate equals hashCode methods on compilation
////@EqualsAndHashCode(exclude = {"roles"})
//// A Lombok annotation to generate all arguments included constructor on compilation
//@AllArgsConstructor
//@NoArgsConstructor
//// A Minlia Speedup annotation to generation code on compilation
//
//public class Tenant{// extends AbstractLocalizedEntity {
//
//
//    private String tenantId;
//    private String name;
//
//
//
//}
