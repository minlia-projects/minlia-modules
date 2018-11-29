package com.minlia.modules.qcloud.faceid.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by garen on 2018/4/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcloudFaceIdResult implements Serializable{

    @JsonIgnore
    private String bizSeqNo;
    private String orderNo;
    private String h5faceId;


//    private String appId;
    private String webankAppId;
    private String version;
    private String nonce;
    private String resultType;
    private String userId;
    private String sign;

//    public String getBizSeqNo() {
//        return bizSeqNo;
//    }
//
//    public void setBizSeqNo(String bizSeqNo) {
//        this.bizSeqNo = bizSeqNo;
//    }
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public String getH5faceId() {
//        return h5faceId;
//    }
//
//    public void setH5faceId(String h5faceId) {
//        this.h5faceId = h5faceId;
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    public String getWebankAppId() {
//        return webankAppId;
//    }
//
//    public void setWebankAppId(String webankAppId) {
//        this.webankAppId = webankAppId;
//    }
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getNonce() {
//        return nonce;
//    }
//
//    public void setNonce(String nonce) {
//        this.nonce = nonce;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getResultType() {
//        return resultType;
//    }
//
//    public void setResultType(String resultType) {
//        this.resultType = resultType;
//    }

}