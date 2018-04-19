package com.minlia.modules.tencent.cloud.auth.body;

import java.io.Serializable;

/**
 * Created by garen on 2018/4/19.
 */
public class TcFaceIdResult implements Serializable{

    private String bizSeqNo;

    private String orderNo;

    private String h5faceId;

    private String sign;
    private String appId;
    private String webankAppId;
    private String version;
    private String nonce;
    private String userId;

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getH5faceId() {
        return h5faceId;
    }

    public void setH5faceId(String h5faceId) {
        this.h5faceId = h5faceId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getWebankAppId() {
        return webankAppId;
    }

    public void setWebankAppId(String webankAppId) {
        this.webankAppId = webankAppId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}