//package com.minlia.module.data.entity.listener;
//
//import com.minlia.cloud.entity.AbstractAuditingEntity;
//
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
//
///**
// * Listener - 创建日期、修改日期处理
// */
//public class AbstractAuditingEntityListener {
//    public AbstractAuditingEntityListener() {
//
//    }
//
//    /**
//     * 保存前处理
//     *
//     * @param entity 基类
//     */
//
//    @PrePersist
//    public void AbstractAuditingEntityPrePersist(AbstractAuditingEntity entity) {
////        entity.setCreatedDate(DateTime.now());
////        entity.setLastModifiedDate(DateTime.now());
//        entity.setCreatedBy("");
//        entity.setLastModifiedBy("");
//
//    }
//
//    /**
//     * 更新前处理
//     *
//     * @param entity 基类
//     */
//    @PreUpdate
//    public void AbstractAuditingEntityPreUpdate(AbstractAuditingEntity entity) {
////        entity.setLastModifiedDate(DateTime.now());
//        entity.setLastModifiedBy("");
//    }
//}
