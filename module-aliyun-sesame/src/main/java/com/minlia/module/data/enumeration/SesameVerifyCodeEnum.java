package com.minlia.module.data.enumeration;

/**
 * Created by garen on 2017/10/9.
 */
public enum SesameVerifyCodeEnum {

    V_CN_NA("查不到身份证信息"),
    V_CN_NM_UM("姓名与身份证号码不匹配"),
    V_CN_NM_MA("姓名与身份证号码匹配");

    private String desc;

    SesameVerifyCodeEnum (String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString(){
        return this.desc;
    }
}
