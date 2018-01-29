//package com.minlia.cloud.tenant.entity;
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import com.minlia.cloud.entity.AbstractAuditingEntity;
//import com.minlia.cloud.entity.AbstractLocalizedEntity;
//import com.minlia.cloud.tenant.listener.AbstractTenantEntityListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.mybatis.annotations.DynamicSearch;
//
//import javax.persistence.EntityListeners;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
///**
// * Created by will on 6/22/17.
// */
//@JsonPropertyOrder({"tenantId"})
//@MappedSuperclass
//@Slf4j
//@EntityListeners(AbstractTenantEntityListener.class)
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.DEFAULT, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)@DynamicSearch
//
//@org.springframework.data.mybatis.annotations.MappedSuperclass
//public class AbstractTenantEntity extends AbstractAuditingEntity {
//
//    /**
//     * REQUESTED-X-TENANTID
//     * 租户的ID
//     */
//    private String tenantId;
////
//    public String getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(String tenantId) {
//        this.tenantId = tenantId;
//    }
//
////    @Override
////
////    @Transient
////    @org.springframework.data.annotation.Transient
////    @JSONField(serialize = false)
////    public int hashCode() {
////        return 17 + (isEmpty() ? 0 : getId().hashCode() * 31);
////    }
////
////    /**
////     * 判断是否相等
////     *
////     * @param obj 对象
////     * @return 是否相等
////     */
////    @Override
////    @Transient
////    @org.springframework.data.annotation.Transient
////    @JSONField(serialize = false)
////    public boolean equals(Object obj) {
////        if (obj == this) {
////            return true;
////        }
////        if (isEmpty() || obj == null || !getClass().isAssignableFrom(obj.getClass())) {
////            return false;
////        }
////        AbstractTenantEntity entity = (AbstractTenantEntity) obj;
////        if (entity.isEmpty()) {
////            return false;
////        }
////        return getId().equals(entity.getId());
////    }
////
////
////    public String toString() {
////        return "AbstractTenantEntity (id=" + this.getId() + ")";
////    }
//
//
//}
