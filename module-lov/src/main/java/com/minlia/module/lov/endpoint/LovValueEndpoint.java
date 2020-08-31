package com.minlia.module.lov.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.approved.bean.ro.ApprovedRO;
import com.minlia.module.approved.entity.Approved;
import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.approved.service.ApprovedService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.Lov;
import com.minlia.module.lov.enntity.LovValue;
import com.minlia.module.lov.servcie.LovService;
import com.minlia.module.lov.servcie.LovValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Lov Value", description = "LOV值")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "lov/value")
public class LovValueEndpoint {

    @Autowired
    private LovValueService lovValueService;

    @Autowired
    private ApprovedService approvedService;

    @Autowired
    private LovService lovService;

//    @AuditLog(value = "create a lov value", type = OperationTypeEnum.CREATE)
//    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.create')")
//    @ApiOperation(value = "创建", httpMethod = "POST")
//    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response create(@Valid @RequestBody LovValue lovValue) {
//        return Response.success(lovValueService.insertSelective(lovValue));
//    }
//
//    @AuditLog(value = "update a lov value", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.update')")
//    @ApiOperation(value = "更新", httpMethod = "PUT")
//    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response update(@Valid @RequestBody LovValue lovValue) {
//        return Response.success(lovValueService.updateByPrimaryKeySelective(lovValue));
//    }

    @AuditLog(value = "create a lov value", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.create')")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody LovValue lovValue) {
        Lov lov = lovService.selectByPrimaryKey(lovValue.getLovId());
        Approved approved = Approved.builder()
                .identifier(lov.getCode()+"&"+lovValue.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_LOV_ITEM_MANAGE_NEW_LOV)
//                .beforeData(JSONObject.toJSONString(lov))
                .afterData(JSONObject.toJSONString(lovValue))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "create a lov value", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.create')")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PutMapping(value = "create/approval", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response create(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            LovValue lovValue = JSONObject.parseObject(approved.getAfterData(),LovValue.class);
            lovValueService.insertSelective(lovValue);
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "update a lov value", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.update')")
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody LovValue lovValue) {
        Lov lov = lovService.selectByPrimaryKey(lovValue.getLovId());
        LovValue oldLovValue = lovValueService.selectByPrimaryKey(lovValue.getId());
        Approved approved = Approved.builder()
                .identifier(lov.getCode()+"&"+oldLovValue.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_LOV_ITEM_MANAGE_EDIT)
                .beforeData(JSONObject.toJSONString(oldLovValue))
                .afterData(JSONObject.toJSONString(lovValue))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "update a lov value", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.update')")
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping(value = "update/approval", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response update(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            LovValue lovValue = JSONObject.parseObject(approved.getAfterData(),LovValue.class);
            lovValueService.updateByPrimaryKeySelective(lovValue);
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "toggle a lov status by id", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.delete')")
    @ApiOperation(value = "启用/禁用")
    @RequestMapping(value = "disable/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response disable(@PathVariable Long id) {
        return Response.success(lovValueService.disable(id));
    }

    @AuditLog(value = "delete a lov value by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('system.lov.delete')")
    @ApiOperation(value = "删除", httpMethod = "DELETE")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        return Response.success(lovValueService.delete(id));
    }

    //    @PreAuthorize(value = "hasAnyAuthority('minlia.lov_value.search')")
//    @ApiOperation(value = "ID查询", httpMethod = "GET")
//    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response one(@PathVariable Long id) {
//        return Response.success(lovValueService.selectByPrimaryKey(id));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov_value.search')")
//    @ApiOperation(value = "集合查询", httpMethod = "POST")
//    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@RequestBody LovValueQRO qro) {
//        return Response.success(lovValueService.selectByAll(qro));
//    }
//
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.search')")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response paginated(@RequestBody LovValueQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> lovValueService.selectByAll(qro));
        return Response.success(pageInfo);
    }

}