package com.minlia.module.pooul.bean.dto;

import java.util.List;

/**
 * Created by garen on 2018/9/6.
 */
public class PooulBlancesDTO extends PooulDTO {

//    {
//        "code": 0,
//            "msg": "success",
//            "data": [
//                {
//                    "_id": "5b85345259376863211d640a",
//                        "account_type": 1,
//                        "balance": -7908,
//                        "created_at": 1535456338,
//                        "merchant_id": "7609332123096874",
//                        "updated_at": 1535612151
//                }
//            ]
//    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        private String _id;

        private String merchant_id;

        private Integer account_type;

        private Integer balance;

        private Integer created_at;

        private Integer updated_at;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public Integer getAccount_type() {
            return account_type;
        }

        public void setAccount_type(Integer account_type) {
            this.account_type = account_type;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Integer created_at) {
            this.created_at = created_at;
        }

        public Integer getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Integer updated_at) {
            this.updated_at = updated_at;
        }
    }

}
