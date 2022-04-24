package com.minlia.module.realname.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRealName3OldDto {

    //当 http响应状态码为200 时，以state参数返回值来做业务判断:
    //返回值为 1 : 查询成功, 三要素一致
    //返回值为 2 : 查询成功, 三要素不一致
    //返回值为 3：查询成功，核验异常
    //{
    //    "request_id": "TID8bf47ab6eda64476973cc5f5b6ebf57e",
    //        "status": "OK",
    //        "state": 1,    //核验结果：1:一致。 2：不一致。3：核验异常（可联系客服查找具体原因）
    //        "result_code": 0,
    //        "result_message": "手机三要素一致"
    //}

    //{"status":"QUERY_FAILED","state":4,"result_code":9003,"result_message":"查无信息","request_id":"TID2c043f6a3fab454eb314cc4a36c08537"}

    @JsonProperty("status")
    private String status;
    @JsonProperty("state")
    private Integer state;
    @JsonProperty("result_code")
    @SerializedName(value = "result_code",alternate = {})
    private Integer resultCode;
    @JsonProperty("result_message")
    @SerializedName(value = "result_message",alternate = {})
    private String resultMessage;
    @JsonProperty("request_id")
    @SerializedName(value = "request_id",alternate = {})
    private String requestId;

    @JsonProperty("reason")
    private String reason;

    public boolean isSuccess() {
        return this.status.equals("OK");
    }

    public boolean isSuccessState() {
        return this.state == 1;
    }

}