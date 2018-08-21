package com.minlia.modules.starter.swagger.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@Api(description = "用户接口")
@RestController
@RequestMapping("/demoController")
public class DemoController {
 
    @ApiOperation(value = "新增用户" ,  notes="新增注册")
    @RequestMapping(value="/createUser",method= RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public void createUser(){
        System.out.println("createUsera");
    }
 
    @ApiOperation(value = "修改用户" ,  notes="修改用户")
    @RequestMapping(value="/updateUser",method=RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@PageableDefault Pageable pageable) {
        System.out.println("updateUser");
        return "dsf";
    }
 
    @ApiOperation(value = "删除用户" ,  notes="删除用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value="/deleteUser",method=RequestMethod.DELETE)
    public void deleteUser(){
        System.out.println("deleteUser");
    }
 
    @ApiOperation(value = "查询用户" ,  notes="查询用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value="/queryUser",method=RequestMethod.GET)
    public void queryUser(@RequestParam("userId") String userId){
        System.out.println("queryUser:::"+userId);
    }
 
}