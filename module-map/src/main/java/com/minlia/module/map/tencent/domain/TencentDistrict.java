//package com.minlia.module.map.tencent.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import com.minlia.boot.persistence.constant.PersistenceConstants;
//import com.minlia.module.persistence.entity.AbstractAuditingEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name = PersistenceConstants.APPLICATION_TABLE_PREFIX +"TencentDistrict")
//@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "tencentdistrict")
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonPropertyOrder({})
//@JsonIgnoreProperties(value = {})
//public class TencentDistrict extends AbstractAuditingEntity {
//
//    /**
//     * 编号
//     */
//    @JsonProperty
//    @Column(unique = true)
//    private String code;
//
//    /**
//     * 父类编码
//     */
//    private String parentCode;
//
//    /**
//     * 所有行政区域的编号
//     */
//    @JsonProperty
//    private String fullcode;
//
//    /**
//     * 简称
//     */
//    @JsonProperty
//    private String name;
//
//    /**
//     * 全称
//     */
//    @JsonProperty
//    private String fullname;
//
//    /**
//     * 拼音
//     */
//    @JsonProperty
//    private String pinyin;
//
//    /**
//     * 级别
//     */
//    @JsonProperty
//    private Integer level;
//
//    /**
//     * 具体地址
//     */
//    @JsonProperty
//    private String address;
//
//    /**
//     * 经度
//     */
//    @JsonProperty
//    private Double longitude;
//
//    /**
//     * 纬度
//     */
//    @JsonProperty
//    private Double latitude;
//
//    /**
//     *
//     */
//    private String  cidxs;
//
//
//
//}
