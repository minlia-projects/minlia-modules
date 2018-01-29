package com.minlia.modules.security.authentication.credential;

/**
 * Created by will on 8/14/17.
 */
public interface WithCellphoneCredential extends WithGuidCredential {
    /**
     * 手机号码
     *
     * @return
     */
    public String getCellphone();

}
