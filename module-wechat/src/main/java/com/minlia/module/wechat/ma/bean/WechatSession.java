package com.minlia.module.wechat.ma.bean;

import com.google.gson.annotations.SerializedName;

public class WechatSession {
    @SerializedName("session_key")
    private String sessionKey;
    @SerializedName("expires_in")
    private Integer expiresin;
    @SerializedName("openid")
    private String openid;
    @SerializedName("unionid")
    private String unionid;

    public Integer getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(Integer expiresin) {
        this.expiresin = expiresin;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
