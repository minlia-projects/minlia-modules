package com.minlia.module.wechat.ma.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatMaUserQO extends QueryRequest {

    private Long id;

    private String guid;

    private String code;

    private String unionId;

    private String openId;

    private boolean guidNotNull;

}