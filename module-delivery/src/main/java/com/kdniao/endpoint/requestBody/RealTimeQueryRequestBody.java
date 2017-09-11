package com.kdniao.endpoint.requestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by qianyi on 2017/8/26.
 * 实时查询订单请求体
 */
public class RealTimeQueryRequestBody implements Serializable {
    /**
     * OrderCode	String	订单编号	O
     */
    @JsonProperty(value = "orderCode")
    private String orderCode;

    /**
     * ShipperCode	String	快递公司编码	R
     */
    @JsonProperty(value = "shipperCode")
    private String shipperCode;

    /**
     * LogisticCode	String	物流单号	R
     */
    @JsonProperty(value = "logisticCode")
    private String logisticCode;


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }
}
