package com.minlia.aliyun.green.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AliyunGreenImageResult {

    /**
     * msg : OK
     * code : 200
     * data : [{"msg":"OK","code":200,"dataId":"9379cace-3c7f-4b64-b7e7-119779daaaec","extras":{},"results":[{"rate":99.9,"suggestion":"pass","label":"normal","scene":"porn"}],"taskId":"img5RFnCR5sruX5l7UknC4UcQ-1u3eGE","url":"http://static.camelsc.com/2020-09-12/others/56352331-44e1-4fac-bf00-79dcb67a2a02.jpg"}]
     * requestId : 24981B98-8F30-4887-B13E-EA3C1C220022
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
         * dataId : 9379cace-3c7f-4b64-b7e7-119779daaaec
         * extras : {}
         * results : [{"rate":99.9,"suggestion":"pass","label":"normal","scene":"porn"}]
         * taskId : img5RFnCR5sruX5l7UknC4UcQ-1u3eGE
         * url : http://static.camelsc.com/2020-09-12/others/56352331-44e1-4fac-bf00-79dcb67a2a02.jpg
         */

        @JsonProperty("msg")
        private String msg;
        @JsonProperty("code")
        private Integer code;
        @JsonProperty("dataId")
        private String dataId;
        @JsonProperty("extras")
        private ExtrasDTO extras;
        @JsonProperty("results")
        private List<ResultsDTO> results;
        @JsonProperty("taskId")
        private String taskId;
        @JsonProperty("url")
        private String url;

        @NoArgsConstructor
        @Data
        public static class ExtrasDTO {
        }

        @NoArgsConstructor
        @Data
        public static class ResultsDTO {
            /**
             * rate : 99.9
             * suggestion : pass
             * label : normal
             * scene : porn
             */

            @JsonProperty("rate")
            private Double rate;
            @JsonProperty("suggestion")
            private String suggestion;
            @JsonProperty("label")
            private String label;
            @JsonProperty("scene")
            private String scene;
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
        return isSuccess() && data.get(0).getResults().stream().filter(resultsDTO -> "pass".equals(resultsDTO.getSuggestion())).count() == data.get(0).getResults().size();
    }

    public boolean isBlock() {
        return isSuccess() && data.get(0).getResults().stream().filter(resultsDTO -> "block".equals(resultsDTO.getSuggestion())).count() > 0;
    }

    public String getLabel() {
        return data.get(0).getResults().stream().filter(resultsDTO -> "block".equals(resultsDTO.getSuggestion())).findFirst().get().getLabel();
    }

    public static AliyunGreenImageResult format(String result) {
        return new Gson().fromJson(result, AliyunGreenImageResult.class);
    }

    public static AliyunGreenContentResult success() {
        return AliyunGreenContentResult.builder().code(200).msg("OK").build();
    }
}
