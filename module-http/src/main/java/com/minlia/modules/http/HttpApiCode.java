package com.minlia.modules.http;

import com.minlia.cloud.code.ApiCode;

/**
 * Created by will on 8/18/17.
 *
 * TODO: HTTP Exceptions to HttpApiCode
 *
 */
public class HttpApiCode extends ApiCode {


    public HttpApiCode() {
        throw new AssertionError();
    }

    public static final Integer HTTP_REQUEST_ERROR = BASED + 20001;



}
