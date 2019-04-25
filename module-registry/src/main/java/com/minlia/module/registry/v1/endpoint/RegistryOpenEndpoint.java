//package com.minlia.module.registry.v1.endpoint;
//
//import com.minlia.cloud.ro.impl.SuccessResponseBody;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.cloud.holder.ContextHolder;
//import com.minlia.cloud.query.specification.batis.QueryCondition;
//import com.minlia.cloud.query.specification.batis.ro.ApiQueryRequestBody;
//import com.minlia.module.registry.v1.ro.RegistryQueryRequestBody;
//import com.minlia.module.registry.v1.service.RegistryReadOnlyService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by will on 8/27/17.
// */
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "registries/open")
//@Api(tags = "Registry", description = "注册表接口")
//@Slf4j
//public class RegistryOpenEndpoint {
//
//  @Autowired
//  private RegistryReadOnlyService registryReadOnlyService;
//
//  @Cacheable
//  @PreAuthorize(value = "hasAnyAuthority('registry.findAllList')")
//  @ApiOperation(value = "根据编码查询出所有结果", notes = "根据编码查询出所有结果", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "findByCode/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public SuccessResponseBody findAll(
//      @PathVariable String code
//  ) {
//
//    //需求是什么
//    //结果集自动存入redis中, 当需要时直接从redis中获取数据
//    //系统启动时需要从库中读入到redis中去
//    //刷新设置时从redis中移除并重新初始化进来
//
//    return SuccessResponseBody.builder().build();
//  }
//
//
//}
