package com.minlia.module.pooul.service;

import org.springframework.http.HttpHeaders;

/**
 * Created by garen on 2018/9/5.
 */
public interface PooulAuthService {

    String login();

    HttpHeaders getHeaders();

}
