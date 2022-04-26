package com.minlia.module.pay;

import com.google.gson.Gson;
import com.minlia.module.pay.bean.SysAlipayOrderDto;
import org.junit.jupiter.api.Test;

public class OrderTest {

    public static void main(String[] args) {
        //String ss = "{\"alipay_trade_query_response\":{\"msg\":\"Business Failed\",\"code\":\"40004\",\"out_trade_no\":\"1518484983355183105\",\"sub_msg\":\"交易不存在\",\"sub_code\":\"ACQ.TRADE_NOT_EXIST\",\"receipt_amount\":\"0.00\",\"point_amount\":\"0.00\",\"buyer_pay_amount\":\"0.00\",\"invoice_amount\":\"0.00\"},\"alipay_cert_sn\":\"f8c7c9179064683fdbf9b068acbd6241\",\"sign\":\"HKdDsIew0jfep/dlWdOxAGiO2YJhZXAlLkdPC3hJN851MmfU6Hmabgby6t0m+glz0vtGQjCJn0vBQ/Gf0B5dpvlZJrs3XlMIr+pPU5L1cYfVdP1PabEKhUwBOIIBo8fWNYWvibQ1YAgn9KEeC2R4L+ddgIrhklSIu4TvNuVNi2JZQj3SOEltgMrAJWCEp7uHUlJE8iW1VoUjxK1yUW7TVnd9HVg9U8I/Lv2ECR16iNHD4pgABS11MFXVKtbqAVxFGcvVwZKbJ5qh9Ot4bYmnTjvuvEOcWj+q+vvyEoQjSOsn/h4EDI56PdHX0QX0vlcUkfp2cYF7lk/dZc7IoaWeCA\\u003d\\u003d\"}";
        //SysAlipayOrderDto orderDto = new Gson().fromJson(ss, SysAlipayOrderDto.class);
        //System.out.println(orderDto.toString());

        System.out.println(1);
    }

    @Test
    public void t() {
        String ss = "{\"alipay_trade_query_response\":{\"msg\":\"Business Failed\",\"code\":\"40004\",\"out_trade_no\":\"1518484983355183105\",\"sub_msg\":\"交易不存在\",\"sub_code\":\"ACQ.TRADE_NOT_EXIST\",\"receipt_amount\":\"0.00\",\"point_amount\":\"0.00\",\"buyer_pay_amount\":\"0.00\",\"invoice_amount\":\"0.00\"},\"alipay_cert_sn\":\"f8c7c9179064683fdbf9b068acbd6241\",\"sign\":\"HKdDsIew0jfep/dlWdOxAGiO2YJhZXAlLkdPC3hJN851MmfU6Hmabgby6t0m+glz0vtGQjCJn0vBQ/Gf0B5dpvlZJrs3XlMIr+pPU5L1cYfVdP1PabEKhUwBOIIBo8fWNYWvibQ1YAgn9KEeC2R4L+ddgIrhklSIu4TvNuVNi2JZQj3SOEltgMrAJWCEp7uHUlJE8iW1VoUjxK1yUW7TVnd9HVg9U8I/Lv2ECR16iNHD4pgABS11MFXVKtbqAVxFGcvVwZKbJ5qh9Ot4bYmnTjvuvEOcWj+q+vvyEoQjSOsn/h4EDI56PdHX0QX0vlcUkfp2cYF7lk/dZc7IoaWeCA\\u003d\\u003d\"}";
        SysAlipayOrderDto orderDto = new Gson().fromJson(ss, SysAlipayOrderDto.class);
        System.out.println(orderDto.toString());
    }

}