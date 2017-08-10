//package com.qianyi.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.minlia.cloud.entity.AbstractEntity;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
///**
// * Created by qianyi on 2017/8/1.
// */
//@Entity
//@org.springframework.data.mybatis.annotations.Entity(name = "person")
//@Table(name = "Product")
//@Getter
//@Setter
//public class Product extends AbstractEntity{
//
//    @JsonProperty
//    private String name;
//
//
//
//    /**
//     * 建立中间关系
//     */
//    @JsonIgnore
//    private Long categoryId;
//
//
//
//
//    @Transient
//    @JsonProperty
////    @LoadRelationship(value="categoryId")
//    private Category category;
//
//
//
//
//
//}
