//package com.qianyi.domain;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.minlia.cloud.entity.AbstractEntity;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//import java.util.List;
//
///**
// * Created by qianyi on 2017/8/1.
// */
//@Entity
//@org.springframework.data.mybatis.annotations.Entity(name = "person")
//@Table(name = "Category")
//@Getter
//@Setter
//
//
//public class Category extends AbstractEntity{
//
//    @JsonProperty
//    private String name;
//
//    /**
//     * 用于显示
//     */
//    @Transient
//    @org.springframework.data.annotation.Transient
//    private List<Product> products;
//
//}
