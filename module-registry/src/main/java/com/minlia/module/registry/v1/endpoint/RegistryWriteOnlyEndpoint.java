//package com.minlia.module.registry.v1.endpoint;
//
//import com.minlia.cloud.body.StatefulBody;
//import com.minlia.cloud.body.impl.SuccessResponseBody;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.cloud.endpoint.ApiWriteOnlyEndpoint;
//import com.minlia.module.registry.v1.domain.Registry;
//import com.minlia.module.registry.v1.service.RegistryWriteOnlyService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by will on 8/17/17.
// */
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "registry/write")
//@Api(tags = "Registry", description = "注册表接口")
//public class RegistryWriteOnlyEndpoint implements ApiWriteOnlyEndpoint<Registry, Long> {
//
//  @Autowired
//  private RegistryWriteOnlyService registryWriteOnlyService;
//
//  @PreAuthorize(value = "hasAnyAuthority('registry.create')")
//  @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
//  @Override
//  public StatefulBody create(@RequestBody Registry registry) {
//    Registry created = registryWriteOnlyService.save(registry);
//    return SuccessResponseBody.builder().payload(created).build();
//  }
//
//  @PreAuthorize(value = "hasAnyAuthority('registry.update')")
//  @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
//  @Override
//  public StatefulBody update(@RequestBody Registry registry) {
//    Registry updated = registryWriteOnlyService.update(registry);
//    return SuccessResponseBody.builder().payload(updated).build();
//  }
//
//  @PreAuthorize(value = "hasAnyAuthority('registry.delete')")
//  @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//  @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//  @Override
//  public StatefulBody delete(@PathVariable Long id) {
//    registryWriteOnlyService.delete(id);
//    return SuccessResponseBody.builder().build();
//  }
//
//}
