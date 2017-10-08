package com.minlia.cloud.application.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.entity.AbstractAuditingEntity;
import com.minlia.cloud.tenant.listener.AbstractTenantEntityListener;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mybatis.annotations.DynamicSearch;

/**
 * Created by will on 6/22/17.
 */
//@JsonPropertyOrder({"appid"})
@MappedSuperclass
@Slf4j
@EntityListeners(AbstractTenantEntityListener.class)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.DEFAULT, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)@DynamicSearch

@org.springframework.data.mybatis.annotations.MappedSuperclass
public class AbstractApplicationEntity extends AbstractAuditingEntity {

    /**
     * X-REQUEST-TENANT
     * X-REQUEST-APPID
     * 租户的应用ID
     */
//    @ApiModelProperty(value = "应用编号")
////    @JsonProperty(value = "appid")
//    private String appid;
//
//    public String getAppid() {
//        return appid;
//    }
//
//    public void setAppid(String appid) {
//        this.appid = appid;
//    }


}
