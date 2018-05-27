//package com.minlia.modules.rbac.backend.loginlog.endpoint;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.minlia.cloud.body.StatefulBody;
//import com.minlia.cloud.body.impl.SuccessResponseBody;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.modules.rbac.backend.common.constant.RebeccaSecurityConstant;
//import com.minlia.modules.rbac.backend.loginlog.body.LoginLogQueryRequestBody;
//import com.minlia.modules.rbac.backend.loginlog.body.RoleUpdateRequestBody;
//import com.minlia.modules.rbac.backend.loginlog.entity.LoginLog;
//import com.minlia.modules.rbac.backend.loginlog.service.LoginLogService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * Created by will on 6/19/17.
// */
//@Api(tags = "System LoginLog", description = "角色")
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "security/role")
//public class RoleEndpoint {
//
//    @Autowired
//    private LoginLogService loginLogService;
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_CREATE +"')")
//    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody create(@Valid @RequestBody LoginLogQueryRequestBody body) {
//        return SuccessResponseBody.builder().payload(loginLogService.create(body)).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_UPDATE +"')")
//    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody update(@Valid @RequestBody RoleUpdateRequestBody body) {
//        return SuccessResponseBody.builder().payload(loginLogService.update(body)).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_DELETE +"')")
//    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{code}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody delete(@PathVariable String code) {
//        loginLogService.delete(code);
//        return SuccessResponseBody.builder().build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_GRANT +"')")
//    @ApiOperation(value = "授权", notes = "授权", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "grant/{code}", consumes = {MediaType.APPLICATION_JSON_VALUE},  produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody grantPermissions(@PathVariable String code, @RequestBody List<Long> permissions) {
//        loginLogService.grantPermission(code,permissions);
//        return SuccessResponseBody.builder().message("授权成功").build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
//    @ApiOperation(value = "根据ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "queryById/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody queryById(@PathVariable Long id) {
//        return SuccessResponseBody.builder().payload(loginLogService.queryById(id)).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
//    @ApiOperation(value = "根据CODE查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "queryByCode/{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody queryByCode(@PathVariable String code) {
//        return SuccessResponseBody.builder().payload(loginLogService.queryByCode(code)).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
//    @ApiOperation(value = "根据GUID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "queryByGuid/{guid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody queryByGuid(@PathVariable String guid) {
//        return SuccessResponseBody.builder().payload(loginLogService.queryByGuid(guid)).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
//    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "queryPage", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody queryPage(@PageableDefault Pageable pageable) {
////        Page page = loginLogService.queryPage(pageable);
//
//        PageHelper.startPage(pageable.getOffset(), pageable.getPageSize());
//        List<LoginLog> roles =  loginLogService.queryList();
//        PageInfo<LoginLog> pageInfo = new PageInfo<LoginLog>(roles);
//        return SuccessResponseBody.builder().payload(pageInfo).build();
//    }
//
//}
