package com.minlia.modules.security.authentication.credential;

/**
 * Created by will on 8/14/17.
 */
public interface WithEmailCredential extends Credential {

    /**
     * 邮箱
     * @return
     */
    String getEmail();

}
