package com.minlia.module.bank.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 银行
 * Created by garen on 2018/8/7.
 */
@Data
public class BankDO {

    /**
     * 编号 感觉不需要
     */
    @JsonIgnore
    private String number;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

}
