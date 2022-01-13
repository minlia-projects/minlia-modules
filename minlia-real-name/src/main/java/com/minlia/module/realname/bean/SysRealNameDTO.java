package com.minlia.module.realname.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SysRealNameDTO {

    /*温馨提示：
        1.解析结果时，先判断code
        2.出现'无记录'时，有以下几种原因
            (1)现役军人、武警官兵、特殊部门人员及特殊级别官员；
            (2)退役不到2年的军人和士兵（根据军衔、兵种不同，时间会有所不同，一般为2年）；
            (3)户口迁出，且没有在新的迁入地迁入；
            (4)户口迁入新迁入地，当地公安系统未将迁移信息上报到公安部（上报时间地域不同而有所差异）；
            (5)更改姓名，当地公安系统未将更改信息上报到公安部（上报时间因地域不同而有所差异）；
            (6)移民；
            (7)未更换二代身份证；
            (8)死亡。
            (9)身份证号确实不存在

    {
        "code": "0", //返回码，0：成功，非0：失败（详见错误码定义）
        //当code=0时，再判断下面result中的res；当code!=0时，表示调用已失败，无需再继续
        "message": "成功", //返回码说明
        "result": {
            "name": "冯天", //姓名
            "idcard": "350301198011129422", //身份证号
            "res": "1", //核验结果状态码，1 一致；2 不一致；3 无记录
            "description": "一致",  //核验结果状态描述
            "sex": "男",
            "birthday": "19940320",
            "address": "江西省南昌市东湖区"
        }
    }

    错误码	错误信息	                描述
    0	    成功	                    成功
    400	    参数错误	                参数错误
    20010	身份证号为空或非法	        身份证号为空或非法
    20310	姓名为空或非法	            姓名为空或非法
    404	    请求资源不存在	            请求资源不存在
    500	    系统内部错误，请联系服务商	系统内部错误，请联系服务商
    501	    第三方服务异常	            第三方服务异常
    604	    接口停用	                接口停用
    1001	其他，以实际返回为准	    其他，以实际返回为准*/

    /**
     * code : 0
     * message : 成功
     * result : {"name":"冯天","idcard":"350301198011129422","res":"1","description":"一致","sex":"男","birthday":"19940320","address":"江西省南昌市东湖区"}
     */
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("result")
    private ResultDTO result;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        /**
         * name : 冯天
         * idcard : 350301198011129422
         * res : 1
         * description : 一致
         * sex : 男
         * birthday : 19940320
         * address : 江西省南昌市东湖区
         */

        @JsonProperty("name")
        private String name;
        @JsonProperty("idcard")
        private String idcard;
        @JsonProperty("res")
        private Integer res;
        @JsonProperty("description")
        private String description;
        @JsonProperty("sex")
        private String sex;
        @JsonProperty("birthday")
        private String birthday;
        @JsonProperty("address")
        private String address;

        public boolean isSuccess() {
            return res == 1;
        }
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean isFailure() {
        return code != 0;
    }

}