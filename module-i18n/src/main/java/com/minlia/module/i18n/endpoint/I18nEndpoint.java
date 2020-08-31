package com.minlia.module.i18n.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.approved.bean.ro.ApprovedRO;
import com.minlia.module.approved.constant.ApprovedSecurityConstant;
import com.minlia.module.approved.entity.Approved;
import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.approved.service.ApprovedService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.i18n.bean.I18nCRO;
import com.minlia.module.i18n.bean.I18nQRO;
import com.minlia.module.i18n.bean.I18nURO;
import com.minlia.module.i18n.constant.I18nConstants;
import com.minlia.module.i18n.entity.I18n;
import com.minlia.module.i18n.event.I18nReloadEvent;
import com.minlia.module.i18n.resource.MessageSource;
import com.minlia.module.i18n.service.I18nService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2018/8/20.
 */
@Api(tags = "System I18n", description = "系统国际化")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "i18n")
public class I18nEndpoint {

    @Autowired
    private I18nService i18nService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApprovedService approvedService;

//    @AuditLog(value = "create i18n info", type = OperationTypeEnum.CREATE)
//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
//    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response create(@Valid @RequestBody I18nCRO cto) {
//        return Response.success(i18nService.create(cto));
//    }
//
//    @AuditLog(value = "update i18n info", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_UPDATE + "')")
//    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response update(@Valid @RequestBody I18nURO uto) {
//        return Response.success(i18nService.update(uto));
//    }
//
//    @AuditLog(value = "create i18n info", type = OperationTypeEnum.DELETE)
//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
//    @ApiOperation(value = "删除")
//    @DeleteMapping(value = "{id}")
//    public Response delete(@PathVariable Long id) {
//        i18nService.delete(id);
//        return Response.success();
//    }

    @AuditLog(value = "create i18n info", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@Valid @RequestBody I18nCRO cto) {
        Approved approved = Approved.builder()
                .identifier(cto.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_I18N_NEW_CODE)
//                .beforeData(JSONObject.toJSONString(cto))
                .afterData(JSONObject.toJSONString(cto))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "create i18n info", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "create/approval", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response create(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            I18nCRO cto = JSONObject.parseObject(approved.getAfterData(),I18nCRO.class);
            i18nService.create(cto);
            messageSource.reload();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "update i18n info", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_UPDATE + "')")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@Valid @RequestBody I18nURO uto) {
        I18n i18n = i18nService.queryById(uto.getId());
        Approved approved = Approved.builder()
                .identifier(i18n.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_I18N_EDIT)
                .beforeData(JSONObject.toJSONString(i18n))
                .afterData(JSONObject.toJSONString(uto))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "update i18n info", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "update/approval", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response update(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            I18nURO uto = JSONObject.parseObject(approved.getAfterData(),I18nURO.class);
            i18nService.update(uto);
            messageSource.reload();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "create i18n info", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        I18n i18n = i18nService.queryById(id);
        Approved approved = Approved.builder()
                .identifier(i18n.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_I18N_DELETE)
                .beforeData(JSONObject.toJSONString(i18n))
                .afterData(""+id)
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "create i18n info", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "删除")
    @PutMapping(value = "delete/approval")
    @Transactional
    public Response delete(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            i18nService.delete(Long.valueOf(approved.getAfterData()));
            messageSource.reload();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "reload i18n info", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
    @ApiOperation(value = "重置", notes = "重置", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "reload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response reload() {
        messageSource.reload();
        I18nReloadEvent.onReload();
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "测试", notes = "测试", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "test/{i18nkey:.+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response test(@PathVariable String i18nkey) {
        return Response.success(Lang.get(i18nkey));
    }

    @AuditLog(value = "query i18n info by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryOne(@PathVariable Long id) {
        return Response.success(i18nService.queryById(id));
    }

    @AuditLog(value = "query i18n info as list", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "ID查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryList(@RequestBody I18nQRO qro) {
        return Response.success(i18nService.queryList(qro));
    }

    @AuditLog(value = "query i18n info as pageable", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "ID查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryPage(@PageableDefault Pageable pageable, @RequestBody I18nQRO qro) {
        return Response.success(i18nService.queryPage(qro, pageable));
    }

}
