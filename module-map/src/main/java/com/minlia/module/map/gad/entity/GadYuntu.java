//package com.minlia.module.map.gad.domain;
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
//import javax.persistence.Table;
//
///**
// * Created by garen on 2017/12/25.
// */
//@Entity
//@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX +"gad_yuntu")
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonPropertyOrder({})
//@JsonIgnoreProperties(value = {})
//public class GadYuntu extends AbstractAuditingEntity {
//
//    /**
//     * 万科城71栋、吉祥花园、百瑞达酒店
//     */
//    @Column(length = 20)
//    @JsonProperty
//    private String buildingName;
//
//    /**
//     * 单元号：A座、71栋
//     */
//    @Column(length = 10)
//    @JsonProperty
//    private String unitNumber;
//
//    /**
//     * 经纬度
//     */
//    @JsonProperty
//    private String location;
//
//    /**
//     * 详细地址
//     */
//    @JsonProperty
//    private String address;
//
//}
