package com.minlia.module.common.bean.response;

import lombok.Data;

import java.util.List;

@Data
public class AlipayBankCardValidateResponse {

//    {"cardType":"CC","bank":"HSBC","key":"1544450219938-6066-11.235.49.122-1772630338","messages":[],"validated":true,"stat":"ok"}
//    {"messages":[{"errorCodes":"CARD_BIN_NOT_MATCH","name":"cardNo"}],"validated":false,"stat":"ok","key":"62510255023294771"}

    private String cardType;

    private String bank;

    private String key;

    private List messages;

    private Boolean validated;

    private String stat;
}
