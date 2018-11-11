package com.minlia.module.wechat.ma.bean.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatMaUserQO {

    private Long id;

    private String guid;

    private String code;

    private String unionId;

}
