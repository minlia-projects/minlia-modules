package com.minlia.module.riskcontrol.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApiIdempotentTokenService {

    /**
     * 创建
     */
    String create(HttpServletRequest request);

    /**
     * 校验
     */
    boolean check(HttpServletRequest request, HttpServletResponse response) throws Exception;

}