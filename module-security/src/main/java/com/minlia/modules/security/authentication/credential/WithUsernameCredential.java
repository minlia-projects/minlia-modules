package com.minlia.modules.security.authentication.credential;

/**
 * Created by will on 8/14/17.
 */
public interface WithUsernameCredential extends WithGuidCredential {
    /**
     * 用户名
     *
     * @return
     */
    public String getUsername();

}
