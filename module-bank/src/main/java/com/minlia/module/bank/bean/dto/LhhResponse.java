package com.minlia.module.bank.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.bank.bean.domain.BankBranchDO;
import lombok.Data;

import java.util.List;

/**
 * Created by garen on 2018/8/7.
 */
@Data
public class LhhResponse {

//    {
//        "error_code": 0,
//            "reason": "success",
//            "result": {
//        "paging": {
//            "total": "419",
//                    "page_total": 21,
//                    "page_size": 20,
//                    "page_now": 1
//        },
//        "list": [
//        {
//            "province": "上海市",
//                "city": "上海市",
//                "district": "金山区",
//                "street": "学府路",
//                "address": "上海市金山区学府路780、782、784、786、788号",
//                "bankname": "中国工商银行",
//                "branchname": "中国工商银行股份有限公司上海市金山卫支行",
//                "number": "102290000259",
//                "phone": "021-67262357",
//                "lat": "30.737488",
//                "lng": "121.32851"
//        },
//        {
//            "province": "上海市",
//                "city": "上海市",
//                "district": "虹口区",
//                "street": "天潼路",
//                "address": "上海市天潼路133号2-5楼",
//                "bankname": "中国工商银行",
//                "branchname": "中国工商银行票据营业部",
//                "number": "102290088875",
//                "phone": "021-63571553",
//                "lat": "31.251144",
//                "lng": "121.49698"
//        }
//    ]
//    }
//    }

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("reason")
    private String reason;

    private Result result;

    public class Result {

        private Paging paging;

        private List<BankBranchDO> list;

        public Paging getPaging() {
            return paging;
        }

        public void setPaging(Paging paging) {
            this.paging = paging;
        }

        public List<BankBranchDO> getList() {
            return list;
        }

        public void setList(List<BankBranchDO> list) {
            this.list = list;
        }

        public class Paging {
            private Integer total;
            @JsonProperty("page_total")
            private Integer pageTotal;
            @JsonProperty("page_size")
            private Integer pageSize;
            @JsonProperty("page_now")
            private Integer pageNow;

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public Integer getPageTotal() {
                return pageTotal;
            }

            public void setPageTotal(Integer pageTotal) {
                this.pageTotal = pageTotal;
            }

            public Integer getPageSize() {
                return pageSize;
            }

            public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize;
            }

            public Integer getPageNow() {
                return pageNow;
            }

            public void setPageNow(Integer pageNow) {
                this.pageNow = pageNow;
            }
        }

    }

    public Boolean isSuccess() {
        return errorCode == 0;
    }

}
