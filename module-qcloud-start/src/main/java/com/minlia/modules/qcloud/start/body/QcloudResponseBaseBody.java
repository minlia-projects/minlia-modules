package com.minlia.modules.qcloud.start.body;

import java.io.Serializable;

/**
 * Created by garen on 2018/4/19.
 */
public class QcloudResponseBaseBody implements Serializable {

    private Integer code;

    private String msg;

    private String bizSeqNo;

    private String transactionTime;

    public boolean isSuccess(){
        return code.equals(0);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

}
