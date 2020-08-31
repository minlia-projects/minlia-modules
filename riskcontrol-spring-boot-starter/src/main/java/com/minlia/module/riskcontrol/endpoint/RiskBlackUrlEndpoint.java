package com.minlia.module.riskcontrol.endpoint;

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
import com.minlia.module.riskcontrol.bean.RiskBlackUrlQRO;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskBlackUrl;
import com.minlia.module.riskcontrol.entity.RiskIpList;
import com.minlia.module.riskcontrol.mapper.RiskBlackUrlMapper;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Url List", description = "风控-URL List")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/url/list")
public class RiskBlackUrlEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskBlackUrlService riskBlackUrlService;

    @Autowired
    private RiskBlackUrlMapper riskBlackUrlMapper;

    @Autowired
    private ApprovedService approvedService;

//    @AuditLog(value = "save fraud url list", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SAVE + "')")
//    @ApiOperation(value = "保存")
//    @PostMapping(value = "")
//    public Response save(@Valid @RequestBody RiskBlackUrl riskBlackUrl) {
//        riskBlackUrlService.pub(riskBlackUrl);
//        return Response.success();
//    }
    @AuditLog(value = "save fraud url list", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SAVE + "')")
    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskBlackUrl riskBlackUrl) {
        Approved approved = Approved.builder()
                .identifier(riskBlackUrl.getType()+"&"+riskBlackUrl.getValue())
                .function(ApprovedFunctionEnum.RISK_MANAGE_API_CONFIG_NEW_API)
                .afterData(JSONObject.toJSONString(riskBlackUrl))
                .build();
        if(null != riskBlackUrl.getId()){
            RiskBlackUrl oldRiskBlackUrl = riskBlackUrlService.queryById(riskBlackUrl.getId());
            if(null != oldRiskBlackUrl){
                approved.setIdentifier(riskBlackUrl.getType()+"&"+riskBlackUrl.getValue());
                approved.setBeforeData(JSONObject.toJSONString(oldRiskBlackUrl));
                approved.setFunction(ApprovedFunctionEnum.RISK_MANAGE_API_CONFIG_EDIT);
            }
        }
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "save fraud url list", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "保存")
    @PutMapping(value = "save/approval")
    @Transactional
    public Response save(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            RiskBlackUrl riskBlackUrl = JSONObject.parseObject(approved.getAfterData(),RiskBlackUrl.class);
            riskBlackUrlService.pub(riskBlackUrl);
            riskBlackUrlService.updateCache();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "reset black url list", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_RESET + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskBlackUrlService.updateCache();
        return Response.success();
    }

//    @AuditLog(value = "delete fraud url list by id", type = OperationTypeEnum.DELETE)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_DELETE + "')")
//    @ApiOperation(value = "ID删除")
//    @DeleteMapping(value = "{id}")
//    public Response delete(@PathVariable Long id) {
//        riskBlackUrlService.delete(id);
//        return Response.success();
//    }
    @AuditLog(value = "delete fraud url list by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_DELETE + "')")
    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        RiskBlackUrl riskBlackUrl = riskBlackUrlService.queryById(id);
        Approved approved = Approved.builder()
                .identifier(riskBlackUrl.getType()+"&"+riskBlackUrl.getValue())
                .function(ApprovedFunctionEnum.RISK_MANAGE_API_CONFIG_DELETE)
                .beforeData(JSONObject.toJSONString(riskBlackUrl))
                .afterData(""+id)
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "delete fraud url list by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "ID删除")
    @PutMapping(value = "delete/approval")
    @Transactional
    public Response delete(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            Long id = Long.valueOf(approved.getAfterData());
            riskBlackUrlService.delete(id);
            riskBlackUrlService.updateCache();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "query fraud url list by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskBlackUrlService.queryById(id));
    }

    @AuditLog(value = "query fraud url list ", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SEARCH + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskBlackUrlService.getAll());
    }

    @AuditLog(value = "query fraud url list as paginated", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskBlackUrlQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskBlackUrlMapper.selectByAll(mapper.map(qro, RiskBlackUrl.class)));
        return Response.success(pageInfo);
    }

}
