package com.minlia.modules.security.authentication.credential;

/**
 * Created by will on 8/14/17.
 */
public interface WithCellphoneCredential extends Credential {
    /**
     * 手机号码
     *
     * @return
     */
    String getCellphone();

}
