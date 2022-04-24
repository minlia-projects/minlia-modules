package com.minlia.module.riskcontrol.service.impl;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.redis.util.RedisUtils;
import com.minlia.module.riskcontrol.constant.RiskcontrolCode;
import com.minlia.module.riskcontrol.constant.RiskcontrolConstants;
import com.minlia.module.riskcontrol.service.ApiIdempotentTokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class ApiIdempotentTokenServiceImpl implements ApiIdempotentTokenService {

    @Override
    public String create(HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        RedisUtils.set(getKey(request), token, 30L);
        return token;
    }

    @Override
    public boolean check(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取前段传来的token
        String token = request.getHeader(RiskcontrolConstants.ApiIdempotent.TOKEN_NAME);
        ApiAssert.hasLength(token, RiskcontrolCode.Message.NO_REPEAT_SUBMIT);
        //获取缓存中的token
        String key = getKey(request);
        Object findToken = RedisUtils.get(key);
        ApiAssert.notNull(findToken, RiskcontrolCode.Message.NO_REPEAT_SUBMIT);
        //判断token是否一致
        ApiAssert.state(token.equals(findToken), RiskcontrolCode.Message.NO_REPEAT_SUBMIT);
        //重新缓存token
        token = create(request);
        response.setHeader(RiskcontrolConstants.ApiIdempotent.TOKEN_NAME, token);
        return true;
    }

    public String getKey(HttpServletRequest request) {
        return RiskcontrolConstants.ApiIdempotent.API_IDEMPOTENT_PREFIX + request.getSession().getId();
    }

}