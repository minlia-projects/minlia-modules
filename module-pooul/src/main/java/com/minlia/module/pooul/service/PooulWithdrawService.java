package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;

/**
 * 对外转账
 * 平台商户可以使用次功能将结余对外转至银行卡，支持平台商户、入驻商户对外转账至同名银行卡，需开通民生银行银企直连转账交易功能
 * Created by garen on 2018/9/21.
 */
public interface PooulWithdrawService {


    Response withdraw();


}
