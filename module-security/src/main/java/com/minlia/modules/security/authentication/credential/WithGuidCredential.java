package com.minlia.modules.security.authentication.credential;

/**
 * Created by will on 8/14/17.
 */
public interface WithGuidCredential extends Credential {

    /**
     * Global User Identity
     *
     * @return
     */
    public String getGuid();


}
