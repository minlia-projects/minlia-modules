package com.minlia.aliyun.green.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AliyunGreenResult {

    /**
     * msg : OK
     * code : 200
     * data : [{"msg":"OK","code":200,"dataId":"ba93e979-f3c4-420d-bba8-1c798a7e8f44","results":[{"rate":100,"suggestion":"block","details":[{"label":"contraband"},{"label":"politics"}],"label":"contraband","scene":"antispam"}],"content":"本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932","taskId":"txt4pwuZLcHbQJ4Qr$tn2CLn7-1tZyip"}]
     * requestId : 235B9EFF-1145-4951-8E03-E5E3FE8E0543
     */

    @JsonProperty("msg")
    private String msg;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("data")
    private List<DataDTO> data;
    @JsonProperty("requestId")
    private String requestId;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * msg : OK
         * code : 200
         * dataId : ba93e979-f3c4-420d-bba8-1c798a7e8f44
         * results : [{"rate":100,"suggestion":"block","details":[{"label":"contraband"},{"label":"politics"}],"label":"contraband","scene":"antispam"}]
         * content : 本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932
         * taskId : txt4pwuZLcHbQJ4Qr$tn2CLn7-1tZyip
         */

        @JsonProperty("msg")
        private String msg;
        @JsonProperty("code")
        private Integer code;
        @JsonProperty("dataId")
        private String dataId;
        @JsonProperty("results")
        private List<ResultsDTO> results;
        @JsonProperty("content")
        private String content;
        @JsonProperty("taskId")
        private String taskId;

        @NoArgsConstructor
        @Data
        public static class ResultsDTO {
            /**
             * rate : 100.0
             * suggestion : block
             * details : [{"label":"contraband"},{"label":"politics"}]
             * label : contraband
             * scene : antispam
             */

            @JsonProperty("rate")
            private Double rate;
            @JsonProperty("suggestion")
            private String suggestion;
            @JsonProperty("details")
            private List<DetailsDTO> details;
            @JsonProperty("label")
            private String label;
            @JsonProperty("scene")
            private String scene;

            @NoArgsConstructor
            @Data
            public static class DetailsDTO {
                /**
                 * label : contraband
                 */
                @JsonProperty("label")
                private String label;
            }
        }
    }

    public boolean isSuccess() {
        return 200 == code;
    }

    /**
     * pass：文本正常，可以直接放行。
     * review：文本需要进一步人工审核。
     * block：文本违规，可以直接删除或者限制公开。
     */
    public boolean isPass() {
        return isSuccess() && "pass".equals(data.get(0).getResults().get(0).getSuggestion());
    }

    public boolean isBlock() {
        return isSuccess() && "block".equals(data.get(0).getResults().get(0).getSuggestion());
    }

    public String getLabel() {
        return isSuccess() && isBlock() ? data.get(0).getResults().get(0).getLabel() : null;
    }

    public static AliyunGreenResult format(String result) {
        return new Gson().fromJson(result, AliyunGreenResult.class);
    }

}