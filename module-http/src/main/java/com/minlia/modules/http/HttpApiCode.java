package com.minlia.modules.http;

import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.code.ApiCode;

/**
 * Created by will on 8/18/17.
 * <p>
 * TODO: HTTP Exceptions to HttpApiCode
 */
@Localized
public class HttpApiCode extends ApiCode {

    public HttpApiCode() {
        throw new AssertionError();
    }

    public static final Integer HTTP_REQUEST_ERROR_BASEDON = BASED_ON + 20001;


}
