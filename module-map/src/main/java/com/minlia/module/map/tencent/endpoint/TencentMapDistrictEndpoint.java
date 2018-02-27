//package com.minlia.module.map.tencent.endpoint;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.map.tencent.body.TencentDistrictQueryRequestBody;
//import com.minlia.module.map.tencent.constants.TencentDistrictSecurityContants;
//import com.minlia.module.map.tencent.service.TencentDistrictService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by Calvin On 2017/12/14.
// */
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "district")
//@Api(tags = "地图区域", description = "地图区域")
//@Slf4j
//public class TencentMapDistrictEndpoint {
//
//    @Autowired
//    private TencentDistrictService service;
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ TencentDistrictSecurityContants.ENTITY_CREATE +"')")
//    @ApiOperation(value = "区域初始化", notes = "区域初始化(省、市、区、县)", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
////    @GetMapping(value = "init", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody init() {
//       return service.initAllDstrict();
//    }
//}
