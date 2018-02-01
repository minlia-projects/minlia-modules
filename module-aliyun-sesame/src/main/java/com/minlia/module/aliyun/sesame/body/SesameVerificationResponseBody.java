package com.minlia.module.aliyun.sesame.body;

import com.minlia.module.aliyun.sesame.enumeration.SesameVerifyCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesameVerificationResponseBody {

    private Boolean success;

    private String code;

    private String message;

    private SesameVerificationData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SesameVerificationData getData() {
        return data;
    }

    public void setData(SesameVerificationData data) {
        this.data = data;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public class SesameVerificationData {

        private String bizNo;

//        private String[] verifyCode;
        private List<SesameVerifyCodeEnum> verifyCode;

        public String getBizNo() {
            return bizNo;
        }

        public void setBizNo(String bizNo) {
            this.bizNo = bizNo;
        }

        public List<SesameVerifyCodeEnum> getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(List<SesameVerifyCodeEnum> verifyCode) {
            this.verifyCode = verifyCode;
        }
    }

}
