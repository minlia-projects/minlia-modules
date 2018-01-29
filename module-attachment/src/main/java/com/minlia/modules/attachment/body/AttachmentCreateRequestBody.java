package com.minlia.modules.attachment.body;


import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "附件-创建")
@Data
public class AttachmentCreateRequestBody implements ApiRequestBody {

    @ApiModelProperty(value = "业务类型编码", example = "account.identity.frontend")
    @NotNull
    private String businessType;

    @ApiModelProperty(value = "业务ID", example = "123")
    @NotNull
    private String businessId;

    @ApiModelProperty(value = "数据")
    @NotNull
    private List<AttachmentData> data;

//    public class AttachmentData {
//
//        @ApiModelProperty(value = "附件类型",example = "jpg")
//        @NotNull
//        private String url;
//
//        @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
//        @NotNull
//        private String key;
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//    }

}
