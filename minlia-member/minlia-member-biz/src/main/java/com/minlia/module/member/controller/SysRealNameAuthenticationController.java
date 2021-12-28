//package com.minlia.module.member.controller;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.minlia.cloud.body.Response;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.module.audit.annotation.AuditLog;
//import com.minlia.module.audit.enums.AuditOperationTypeEnum;
//import com.minlia.module.dozer.util.DozerUtils;
//import com.minlia.module.member.bean.SysAuthenticationRo;
//import com.minlia.module.member.bean.SysMemberAuthenticationSro;
//import com.minlia.module.member.bean.SysPersonalAuthenticationQro;
//import com.minlia.module.member.constant.SysMemberConstants;
//import com.minlia.module.member.entity.SysPersonalAuthenticationEntity;
//import com.minlia.module.member.service.SysPersonalAuthenticationService;
//import com.minlia.module.rebecca.context.SecurityContextHolder;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
///**
// * <p>
// * 个人认证 前端控制器
// * </p>
// *
// * @author garen
// * @since 2020-09-08
// */
//@Api(tags = "System real name", description = "实名认证")
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "member/real-name")
//public class SysRealNameAuthenticationController {
//
//    private final SysPersonalAuthenticationService sysPersonalAuthenticationService;
//
//    public SysRealNameAuthenticationController(SysPersonalAuthenticationService sysPersonalAuthenticationService) {
//        this.sysPersonalAuthenticationService = sysPersonalAuthenticationService;
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.AUTH + "')")
//    @AuditLog(type = AuditOperationTypeEnum.CREATE)
//    @ApiOperation(value = "认证")
//    @PostMapping
//    public Response authentication(@Valid @RequestBody SysMemberAuthenticationSro sro) {
//        return Response.success(sysPersonalAuthenticationService.authentication(sro));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.REVIEW + "')")
//    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
//    @ApiOperation(value = "审核")
//    @PutMapping(value = "review")
//    public Response review(@Valid @RequestBody SysAuthenticationRo ro) {
//        return Response.success(sysPersonalAuthenticationService.review(ro));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.LATEST + "')")
//    @AuditLog(type = AuditOperationTypeEnum.SELECT)
//    @ApiOperation(value = "最新")
//    @GetMapping(value = "latest")
//    public Response latest() {
//        return Response.success(sysPersonalAuthenticationService.latest());
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.READ + "')")
//    @AuditLog(type = AuditOperationTypeEnum.SELECT)
//    @ApiOperation(value = "我的")
//    @GetMapping(value = "me")
//    public Response me() {
//        return Response.success(sysPersonalAuthenticationService.getOne(Wrappers.<SysPersonalAuthenticationEntity>lambdaQuery().eq(SysPersonalAuthenticationEntity::getUid, SecurityContextHolder.getUid())));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.SELECT + "')")
//    @AuditLog(type = AuditOperationTypeEnum.SELECT)
//    @ApiOperation(value = "ID查询")
//    @GetMapping(value = "{id}")
//    public Response id(@PathVariable Long id) {
//        return Response.success(sysPersonalAuthenticationService.getById(id));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + SysMemberConstants.Authorize.SELECT + "')")
//    @AuditLog(type = AuditOperationTypeEnum.SELECT)
//    @ApiOperation(value = "分页查询")
//    @PostMapping(value = "page")
//    public Response page(@Valid @RequestBody SysPersonalAuthenticationQro qro) {
//        LambdaQueryWrapper<SysPersonalAuthenticationEntity> queryWrapper = new QueryWrapper<SysPersonalAuthenticationEntity>()
//                .lambda().setEntity(DozerUtils.map(qro, SysPersonalAuthenticationEntity.class));
//        return Response.success(sysPersonalAuthenticationService.page(qro.getPage(), queryWrapper));
//    }
//
//}