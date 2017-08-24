package com.minlia.modules.security.authentication.credential;

import io.swagger.annotations.ApiModel;

/**
 * Created by will on 8/14/17.
 */
@ApiModel("登录请求体")
public interface Credential {

    /**
     * 密码
     *
     * @return
     */
    public String getPassword();

}
