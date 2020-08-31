package com.minlia.module.lov.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.approved.bean.ro.ApprovedRO;
import com.minlia.module.approved.constant.ApprovedSecurityConstant;
import com.minlia.module.approved.entity.Approved;
import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.approved.service.ApprovedService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.i18n.bean.I18nURO;
import com.minlia.module.lov.bean.LovQRO;
import com.minlia.module.lov.enntity.Lov;
import com.minlia.module.lov.servcie.LovService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Lov", description = "LOV值集")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "lov")
public class LovEndpoint {

    @Autowired
    private LovService lovService;

    @Autowired
    private ApprovedService approvedService;

//    @AuditLog(value = "create a lov", type = OperationTypeEnum.CREATE)
//    @PreAuthorize(value = "hasAnyAuthority('system.lov.create')")
//    @ApiOperation(value = "创建", httpMethod = "POST")
//    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response create(@Valid @RequestBody Lov lov) {
//        return Response.success(lovService.insertSelective(lov));
//    }
//
//    @AuditLog(value = "update a lov", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('system.lov.update')")
//    @ApiOperation(value = "更新", httpMethod = "PUT")
//    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response update(@Valid @RequestBody Lov lov) {
//        return Response.success(lovService.updateByPrimaryKeySelective(lov));
//    }

    @AuditLog(value = "create a lov", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.create')")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody Lov lov) {
        Approved approved = Approved.builder()
                .identifier(lov.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_LOV_NEW_LOV)
//                .beforeData(JSONObject.toJSONString(lov))
                .afterData(JSONObject.toJSONString(lov))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "create a lov", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PutMapping(value = "create/approval", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response create(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            Lov lov = JSONObject.parseObject(approved.getAfterData(),Lov.class);
            lovService.insertSelective(lov);
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "update a lov", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.update')")
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody Lov lov) {
        Lov oldLov = lovService.selectOneByCode(lov.getCode());
        Approved approved = Approved.builder()
                .identifier(oldLov.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_LOV_EDIT)
                .beforeData(JSONObject.toJSONString(oldLov))
                .afterData(JSONObject.toJSONString(lov))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "update a lov", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping(value = "update/approval", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response update(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            Lov lov = JSONObject.parseObject(approved.getAfterData(),Lov.class);
            lovService.updateByPrimaryKeySelective(lov);
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "toggle a lov status by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.delete')")
    @ApiOperation(value = "启用/禁用")
    @RequestMapping(value = "disable/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response disable(@PathVariable Long id) {
        return Response.success(lovService.disable(id));
    }

    @AuditLog(value = "delete a lov status by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.delete')")
    @ApiOperation(value = "删除", httpMethod = "DELETE")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        return Response.success(lovService.delete(id));
    }

    //    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.search')")
//    @ApiOperation(value = "ID查询", httpMethod = "GET")
//    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response one(@PathVariable Long id) {
//        return Response.success(lovService.selectByPrimaryKey(id));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.search')")
//    @ApiOperation(value = "集合查询", httpMethod = "POST")
//    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@RequestBody LovQRO qro) {
//        return Response.success(lovService.selectByAll(qro));
//    }
//
    @PreAuthorize(value = "hasAnyAuthority('system.lov.search')")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response paginated(@RequestBody LovQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> lovService.selectByAll(qro));
        return Response.success(pageInfo);
    }

}