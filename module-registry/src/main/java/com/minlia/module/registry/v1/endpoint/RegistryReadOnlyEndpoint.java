//package com.minlia.module.registry.v1.endpoint;
//
//import com.minlia.cloud.ro.StatefulBody;
//import com.minlia.cloud.ro.impl.SuccessResponseBody;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.cloud.endpoint.ApiReadOnlyEndpoint;
//import com.minlia.cloud.query.specification.batis.ro.ApiQueryRequestBody;
//import com.minlia.module.registry.v1.ro.RegistryQueryRequestBody;
//import com.minlia.module.registry.v1.entity.Registry;
//import com.minlia.module.registry.v1.service.RegistryReadOnlyService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.entity.Page;
//import org.springframework.data.entity.Pageable;
//import org.springframework.data.web.PageableDefault;
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
//@RequestMapping(value = ApiPrefix.V1 + "registries/read")
//@Api(tags = "Registry", description = "注册表接口")
//public class RegistryReadOnlyEndpoint implements
//    ApiReadOnlyEndpoint<RegistryQueryRequestBody, Registry, Long> {
//
//  @Autowired
//  private RegistryReadOnlyService registryReadOnlyService;
//
//  @PreAuthorize(value = "hasAnyAuthority('registry.findAllPage')")
//  @ApiOperation(value = "根据条件查询分页结果", notes = "根据条件查询分页结果", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "findAllPage", produces = MediaType.APPLICATION_JSON_VALUE)
//  public StatefulBody findAll(@RequestBody ApiQueryRequestBody<RegistryQueryRequestBody> ro,
//      @PageableDefault Pageable pageable) {
//    Page found = registryReadOnlyService.findAll(ro, pageable);
//    return SuccessResponseBody.builder().payload(found).build();
//  }
//
//  @PreAuthorize(value = "hasAnyAuthority('registry.findAllList')")
//  @ApiOperation(value = "根据条件查询集合结果", notes = "根据条件查询集合结果", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "findAllList", produces = MediaType.APPLICATION_JSON_VALUE)
//  public SuccessResponseBody findAll(
//      @RequestBody ApiQueryRequestBody<RegistryQueryRequestBody> ro) {
//    return SuccessResponseBody.builder().payload(registryReadOnlyService.findAll(ro)).build();
//  }
//
//  @PreAuthorize(value = "hasAnyAuthority('registry.findOne')")
//  @ApiOperation(value = "根据ID查询单个结果", notes = "根据ID查询单个结果", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//  @Override
//  public StatefulBody findOne(@PathVariable Long id) {
//    Registry found = registryReadOnlyService.findOne(id);
//    return SuccessResponseBody.builder().payload(found).build();
//  }
//
//
//}
