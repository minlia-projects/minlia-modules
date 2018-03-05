package com.minlia.module.wechat.ma.body;


import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatOpenAccountQueryBody {

    @ApiModelProperty(value = "用户编号",example = "1")
    private String guid;

    @ApiModelProperty(value = "联合编号",example = "unionId")
    private String unionId;

    @ApiModelProperty(value = "开放的用户编号",example = "openId")
    private String openId;

    @ApiModelProperty(value = "微信临时编码",example = "wxCode")
    private String wxCode;

    @ApiModelProperty(value = "openId类型",example = "AAA")
    private WechatOpenidType type;

    @ApiModelProperty(value = "openId子项",example = "AAA")
    private String subitem;

    @ApiModelProperty(value = "是否订阅(关注)", example = "false")
    private Boolean isSubscribe;

}
