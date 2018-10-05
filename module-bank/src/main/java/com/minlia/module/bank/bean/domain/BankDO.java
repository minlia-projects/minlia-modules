package com.minlia.module.bank.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.data.entity.WithIdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 银行
 * Created by garen on 2018/8/7.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDO extends WithIdEntity {

    /**
     * 编号 感觉不需要
     */
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
