//package com.minlia.module.data.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.minlia.module.common.constant.LocalDateConstants;
//import com.minlia.module.data.type.AuditableType;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
///**
// * @author william raym at minlia.com
// * Created on: 2020-05-30
// */
//@Data
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@EqualsAndHashCode
//@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
//public class AbstractAuditableEntity extends AbstractStatefulEntity implements AuditableType {
//
//    @CreatedBy
//    @Column(name="created_by", columnDefinition = "varchar(254) null default '10000' COMMENT 'created by'")
//    private String createdBy;
//
//    @LastModifiedBy
//    @Column(name="last_modified_by", columnDefinition = "varchar(254) null default '10000' COMMENT 'last modified by'")
//    private String lastModifiedBy;
//
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @CreatedDate
//    @Column(name = "created_date", columnDefinition = "timestamp default CURRENT_TIMESTAMP COMMENT 'created date'")
//    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
//    private LocalDateTime createdDate;
//
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @LastModifiedDate
//    @Column(name = "last_modified_date", columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'last modified date'")
//    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
//    private LocalDateTime lastModifiedDate;
//
//    @PreUpdate
//    private void preUpdate() {
//        this.lastModifiedDate = LocalDateTime.now();
//    }
//
//    @PrePersist
//    private void prePersist() {
//        this.createdDate = LocalDateTime.now();
//    }
//
////    @PreRemove
////    protected void preRemove() {
////        updateTime = new Date();
////    }
//
//}
