package com.minlia.module.bible.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.approved.bean.ro.ApprovedRO;
import com.minlia.module.approved.constant.ApprovedSecurityConstant;
import com.minlia.module.approved.entity.Approved;
import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.approved.service.ApprovedService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.bible.constant.BibleConstants;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.ro.BibleItemCRO;
import com.minlia.module.bible.ro.BibleItemQRO;
import com.minlia.module.bible.ro.BibleItemURO;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.bible.service.BibleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bible/item")
@Api(tags = "System Bible Item", description = "数据字典")
@Slf4j
public class BibleItemEndpoint {

    @Autowired
    private BibleItemService bibleItemService;

    @Autowired
    private ApprovedService approvedService;

    @Autowired
    private BibleService bibleService;

//    @AuditLog(value = "create system bible item", type = OperationTypeEnum.CREATE)
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.CREATE + "')")
//    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response create(@Valid @RequestBody BibleItemCRO cto) {
//        return Response.success(bibleItemService.create(cto));
//    }
//
//    @AuditLog(value = "update system bible item", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.UPDATE + "')")
//    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response update(@Valid @RequestBody BibleItemURO uto) {
//        return Response.success(bibleItemService.update(uto));
//    }
//
//    @AuditLog(value = "delete system bible item by id", type = OperationTypeEnum.DELETE)
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.DELETE + "')")
//    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response delete(@PathVariable Long id) {
//        bibleItemService.delete(id);
//        return Response.success();
//    }

    @AuditLog(value = "create system bible item", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody BibleItemCRO cto) {
        Approved approved = Approved.builder()
                .identifier(cto.getParentCode()+"&"+cto.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_BIBLES_ITEM_MANAGE_NEW_BIBLE_ITEM)
//                .beforeData(JSONObject.toJSONString(cto))
                .afterData(JSONObject.toJSONString(cto))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "create system bible item", type = OperationTypeEnum.CREATE)
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @RequestMapping(value = "create/approval", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response create(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            BibleItemCRO cto = JSONObject.parseObject(approved.getAfterData(),BibleItemCRO.class);
            bibleItemService.create(cto);
            bibleService.reload();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "update system bible item", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody BibleItemURO uto) {
        BibleItem bibleItem = bibleItemService.queryById(uto.getId());
        Approved approved = Approved.builder()
                .identifier(bibleItem.getParentCode()+"&"+bibleItem.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_BIBLES_ITEM_MANAGE_EDIT)
                .beforeData(JSONObject.toJSONString(bibleItem))
                .afterData(JSONObject.toJSONString(uto))
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "update system bible item", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update/approval", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response update(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            BibleItemURO uto = JSONObject.parseObject(approved.getAfterData(),BibleItemURO.class);
            bibleItemService.update(uto);
            bibleService.reload();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "delete system bible item by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        BibleItem bibleItem = bibleItemService.queryById(id);
        Approved approved = Approved.builder()
                .identifier(bibleItem.getParentCode()+"&"+bibleItem.getCode())
                .function(ApprovedFunctionEnum.SYSTEM_SETTINGS_BIBLES_ITEM_MANAGE_DELETE)
                .beforeData(JSONObject.toJSONString(bibleItem))
                .afterData(""+id)
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "delete system bible item by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "delete/approval", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public Response delete(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            bibleItemService.delete(Long.valueOf(approved.getAfterData()));
            bibleService.reload();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "query system bible item by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryById(@PathVariable Long id) {
        return Response.success(bibleItemService.queryById(id));
    }

    @AuditLog(value = "query system bible item by code", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "根据CODE查询", notes = "根据CODE查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response get(@RequestParam String parentCode, @RequestParam String code) {
        return Response.success(SystemCode.Message.SUCCESS, bibleItemService.get(parentCode, code));
    }

    @AuditLog(value = "query system bible item by parent code", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "父CODE查询", notes = "父CODE查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryByParentCode", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByParentCode(@RequestParam String parentCode) {
        return Response.success(bibleItemService.queryList(BibleItemQRO.builder().parentCode(parentCode).build()));
    }

    @AuditLog(value = "query system bible item by query request body as list", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "查询集合", notes = "查询集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryList(@Valid @RequestBody BibleItemQRO qro) {
        return Response.success(bibleItemService.queryList(qro));
    }

    @AuditLog(value = "query system bible item by query request body as paginated result", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "查询分页", notes = "查询分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryPage", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPage(@PageableDefault Pageable pageable, @RequestBody BibleItemQRO qro) {
        return Response.success(bibleItemService.queryPage(qro, pageable));
    }

}

