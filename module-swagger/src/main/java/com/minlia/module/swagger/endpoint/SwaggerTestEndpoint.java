package com.minlia.module.swagger.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger测试 前端控制器
 *
 * @author garen
 * @version 1.0.0
 * @date 2020-10-24 13:50:09
 */
@Api(tags = "Swagger Test")
@RestController
@RequestMapping("/api/test")
public class SwaggerTestEndpoint {

    @ApiOperation(value = "创建")
    @PostMapping
    public String create(@Validated @RequestBody SwaggerTestBody sro) {
        return "ok";
    }

    @ApiOperation(value = "修改")
    @PutMapping
    public String update(@Validated @RequestBody SwaggerTestBody sro) {
        return "ok";
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public String delete(@PathVariable Long id) {
        return "ok";
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "{id}")
    public String select(@PathVariable Long id) {
        return "ok";
    }

}