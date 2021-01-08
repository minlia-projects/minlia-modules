package com.minlia.module.currency.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SysCurrencyRateDto {

    /**
     * success : 1
     * result : {"cur_base":"CNY","cur_transaction":"USD","cur_date":"2017-05-02","exchange_rate":"6.9311"}
     */

    @JsonProperty("success")
    private Integer success;
    @JsonProperty("result")
    private ResultDTO result;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        /**
         * cur_base : CNY
         * cur_transaction : USD
         * cur_date : 2017-05-02
         * exchange_rate : 6.9311
         */

        @JsonProperty("cur_base")
        private String curBase;
        @JsonProperty("cur_transaction")
        private String curTransaction;
        @JsonProperty("cur_date")
        private String curDate;
        @JsonProperty("exchange_rate")
        private String exchangeRate;
    }

    public boolean isSuccess() {
        return success == 1;
    }

}