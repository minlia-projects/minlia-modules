package com.microsoft.crm.v1.endpoint;

import com.microsoft.crm.v1.dao.UserDao;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 8/2/17.
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Api(tags = "用户1", value = "用户1", description = "用户1")
public class UserEndpoint {


    @Autowired
    UserDao userDao;


    @ApiOperation(
            value = "获取一个指定ID的实体",
            notes = "获取一个指定ID的实体",
            httpMethod = "GET",
            produces = "application/json"
    )
    @GetMapping(value = "")
    public StatefulBody findOne() {
//        userDao.findByOverrridingMethod();
        return SuccessResponseBody.builder().payload( userDao.findUseMapper("x")).build();
    }


}
