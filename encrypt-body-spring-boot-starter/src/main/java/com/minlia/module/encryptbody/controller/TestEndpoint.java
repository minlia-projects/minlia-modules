package com.minlia.module.encryptbody.controller;

import com.minlia.module.encryptbody.annotation.decrypt.DecryptBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/6/20 8:10 PM
 */
@RestController(value = "")
public class TestEndpoint {

    @ResponseBody
    @PostMapping("test")
//    @RSADecryptBody
    @DecryptBody
    public void test(@Valid @RequestBody Test tt){
        System.out.println(tt.getName());
    }
}
