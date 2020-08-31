package com.minlia.module.richtext.endpoint;

import com.alibaba.fastjson.JSONObject;
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
import com.minlia.module.richtext.bean.RichtextCRO;
import com.minlia.module.richtext.bean.RichtextQRO;
import com.minlia.module.richtext.bean.RichtextURO;
import com.minlia.module.richtext.constant.RichtextConstants;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.service.RichtextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Rich Text", description = "富文本")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "richtext")
public class RichtextEndpoint {

    @Autowired
    private RichtextService richtextService;

    @Autowired
    private ApprovedService approvedService;

//    @AuditLog(value = "create richtext", type = OperationTypeEnum.CREATE)
//    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.CREATE + "')")
//    @ApiOperation(value = "create")
//    @PostMapping(value = "create")
//    public Response create(@Valid @RequestBody RichtextCRO cro) {
//        return Response.success(richtextService.create(cro));
//    }
//
//    @AuditLog(value = "update richtext", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.UPDATE + "')")
//    @ApiOperation(value = "update")
//    @PutMapping(value = "update")
//    public Response update(@Valid @RequestBody RichtextURO uro) {
//        return Response.success(richtextService.update(uro));
//    }
//
//    @AuditLog(value = "delete richtext by id", type = OperationTypeEnum.DELETE)
//    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.DELETE + "')")
//    @ApiOperation(value = "delete")
//    @DeleteMapping(value = "delete/{id}")
//    public Response update(@PathVariable Long id) {
//        richtextService.delete(id);
//        return Response.success();
//    }

    @AuditLog(value = "create richtext", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.CREATE + "')")
    @ApiOperation(value = "create")
    @PostMapping(value = "create")
    public Response create(@Valid @RequestBody RichtextCRO cro) {
        Approved approved = Approved.builder()
                .identifier(cro.getCode()+"&"+cro.getType()+"&"+cro.getLocale())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_RICH_TEXT_NEW_RICHTEXT)
//                .beforeData(JSONObject.toJSONString(cro))
                .afterData(JSONObject.toJSONString(cro))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "create richtext", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "create")
    @PutMapping(value = "create/approval")
    @Transactional
    public Response create(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            RichtextCRO cro = JSONObject.parseObject(approved.getAfterData(),RichtextCRO.class);
            richtextService.create(cro);
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "update richtext", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.UPDATE + "')")
    @ApiOperation(value = "update")
    @PutMapping(value = "update")
    public Response update(@Valid @RequestBody RichtextURO uro) {
        Richtext richtext =richtextService.queryById(uro.getId());
        Approved approved = Approved.builder()
                .identifier(richtext.getCode()+"&"+richtext.getType()+"&"+richtext.getLocale())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_RICH_TEXT_EDIT)
                .beforeData(JSONObject.toJSONString(richtext))
                .afterData(JSONObject.toJSONString(uro))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "update richtext", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "update")
    @PutMapping(value = "update/approval")
    @Transactional
    public Response update(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            RichtextURO uro = JSONObject.parseObject(approved.getAfterData(),RichtextURO.class);
            richtextService.update(uro);
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "delete richtext by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.DELETE + "')")
    @ApiOperation(value = "delete")
    @DeleteMapping(value = "delete/{id}")
    public Response delete(@PathVariable Long id) {
        Richtext richtext = richtextService.queryById(id);
        Approved approved = Approved.builder()
                .identifier(richtext.getCode()+"&"+richtext.getType()+"&"+richtext.getLocale())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_RICH_TEXT_DELETE)
                .beforeData(JSONObject.toJSONString(richtext))
                .afterData(""+id)
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "delete richtext by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "delete")
    @PutMapping(value = "delete/approval")
    @Transactional
    public Response delete(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            richtextService.delete(Long.valueOf(approved.getAfterData()));
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "query a richtext by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "id")
    @GetMapping(value = "{id}")
    public Response findOne(@PathVariable Long id) {
        return Response.success(richtextService.queryById(id));
    }

    @AuditLog(value = "query richtexts by query request body as list", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "list")
    @PostMapping(value = "list")
    public Response list(@RequestBody RichtextQRO qro) {
        return Response.success(richtextService.queryList(qro));
    }

    @AuditLog(value = "query richtexts by query request body as paginated result", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "page")
    @PostMapping(value = "page")
    public Response page(@RequestBody RichtextQRO qro) {
        return Response.success(richtextService.queryPage(qro));
    }

}