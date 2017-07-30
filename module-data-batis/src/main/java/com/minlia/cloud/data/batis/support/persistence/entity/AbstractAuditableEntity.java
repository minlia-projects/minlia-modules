package com.minlia.cloud.data.batis.support.persistence.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mybatis.annotations.DynamicSearch;
import org.springframework.data.mybatis.annotations.MappedSuperclass;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Map;


@MappedSuperclass
@javax.persistence.MappedSuperclass
@DynamicSearch
@Data
public abstract class AbstractAuditableEntity<ID extends Serializable> implements Persistable,Serializable {

    /*** 状态 审核 */
    public static final Integer FLAG_AUDIT = 1;
    /*** 状态 正常 */
    public static final Integer FLAG_NORMAL = 0;
    /*** 状态 停用 */
    public static final Integer FLAG_UNABLE = -1;
    /*** 状态 已删除 */
    public static final Integer FLAG_DELETE = -2;
    /**
     * 状态（-2：删除；-1：停用 0：正常 1:审核）
     */
    public static final String F_STATUS = "status";
    /*** ID */
    public static final String F_ID = "id";
    public static final String F_CREATEDBY = "createdBy";
    public static final String F_CREATOR = "creator";
    public static final String F_CREATEDDATE = "createdDate";
    public static final String F_LASTMODIFIEDBY = "lastModifiedBy";
    public static final String F_MODIFIER = "modifier";
    public static final String F_LASTMODIFIEDDATE = "lastModifiedDate";
    public static final String F_VERSION = "version";
    public static final String F_DESCRIPTION = "description";
    private static final long serialVersionUID = 1L;
    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    @javax.persistence.Transient
    @Transient
    @JSONField(serialize = false)
    protected Map<String, Object> paramsMap;

    /**
     * 自定义条件SQL
     */
    @javax.persistence.Transient
    @Transient
    @JSONField(serialize = false)
    protected String sqlConditionDsf;


    @javax.persistence.Transient
    @Transient
    @JsonIgnore
    @XmlTransient
    protected String dbName ="mysql";


    @JsonIgnore
    @XmlTransient
    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public String getSqlConditionDsf() {
        return sqlConditionDsf;
    }

    public void setSqlConditionDsf(String sqlConditionDsf) {
        this.sqlConditionDsf = sqlConditionDsf;
    }


}
