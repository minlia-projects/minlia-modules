package com.minlia.module.riskcontrol.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.service.ApiIdempotentTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Api 幂等
 * </p>
 *
 * @author garen
 * @since 2020-08-18
 */
@Api(tags = "System Api Idempotent Token")
@RestController
@RequestMapping(value = ApiPrefix.API + "/idempotent/token")
@RequiredArgsConstructor
public class ApiIdempotentTokenController {

    private final ApiIdempotentTokenService apiIdempotentTokenService;

    @ApiOperation(value = "获取")
    @GetMapping
    public Response get(HttpServletRequest request, HttpServletResponse response) {
        return Response.success(apiIdempotentTokenService.create(request));
    }

}