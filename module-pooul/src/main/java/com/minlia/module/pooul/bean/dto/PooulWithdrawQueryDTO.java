package com.minlia.module.pooul.bean.dto;

import lombok.Data;

/**
 * Created by garen on 2018/9/25.
 */
@Data
public class PooulWithdrawQueryDTO extends PooulDTO {

    private PooulWithdrawQueryDTO.Data data;

    @lombok.Data
    public static class Data {

        private String _id;

        private int amount;

        private int bank_card_id;

        private String mch_withdraw_id;

        /**
         * 转账状态，1为处理中，2 转账成功，3 转账失败
         */
        private int status;

    }

}
