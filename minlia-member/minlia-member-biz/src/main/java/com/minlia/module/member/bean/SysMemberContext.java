package com.minlia.module.member.bean;

import com.minlia.modules.security.model.UserContext;
import lombok.Data;

/**
 * 会员上下文
 *
 * @author garen
 * @version 1.0
 * @date 2020/9/10 11:29:38
 */
@Data
public class SysMemberContext extends UserContext {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否认证
     */
    private boolean authFlag;

}