package com.minlia.module.swagger.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Swagger测试 前端控制器
 *
 * @author garen
 * @version 1.0.0
 * @date 2020-10-24 13:50:09
 */
@Api("Swagger Test")
@RestController
@RequestMapping("/api/test")
public class SwaggerTestEndpoint {

    @ApiOperation("POST")
    @PostMapping
    public Boolean post() {
        return true;
    }

    @ApiOperation("GET")
    @GetMapping
    public Boolean get() {
        return true;
    }

}