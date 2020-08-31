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
import com.minlia.module.riskcontrol.bean.RiskIpListQRO;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskIpList;
import com.minlia.module.riskcontrol.mapper.RiskIpListMapper;
import com.minlia.module.riskcontrol.service.RiskIpListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Ip List", description = "风控-IP List")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/ip/list")
public class RiskIpListEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskIpListService riskIpListService;

    @Autowired
    private RiskIpListMapper riskIpListMapper;

    @Autowired
    private ApprovedService approvedService;

//    @AuditLog(value = "save fraud ip list", type = OperationTypeEnum.MODIFY)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SAVE + "')")
//    @ApiOperation(value = "保存")
//    @PostMapping(value = "")
//    public Response save(@Valid @RequestBody RiskIpList riskIpList) {
//        riskIpListService.pub(riskIpList);
//        return Response.success();
//    }
    @AuditLog(value = "save fraud ip list", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SAVE + "')")
    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskIpList riskIpList) {
        Approved approved = Approved.builder()
                .identifier(riskIpList.getCountry()+"&"+riskIpList.getStart())
                .function(ApprovedFunctionEnum.RISK_MANAGE_IP_CONFIG_NEW_IP)
                .afterData(JSONObject.toJSONString(riskIpList))
                .build();
        if(null != riskIpList.getId()){
            RiskIpList oldRiskIpList = riskIpListService.queryById(riskIpList.getId());
            if(null != oldRiskIpList){
                approved.setIdentifier((oldRiskIpList.getCountry()+"&"+oldRiskIpList.getStart()));
                approved.setBeforeData(JSONObject.toJSONString(oldRiskIpList));
                approved.setFunction(ApprovedFunctionEnum.RISK_MANAGE_IP_CONFIG_EDIT);
            }
        }
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "save fraud ip list", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "保存")
    @PutMapping(value = "save/approval")
    @Transactional
    public Response save(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            RiskIpList riskIpList = JSONObject.parseObject(approved.getAfterData(),RiskIpList.class);
            riskIpListService.pub(riskIpList);
            riskIpListService.updateCache();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "reset black ip list", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_RESET + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskIpListService.updateCache();
        return Response.success();
    }

//    @AuditLog(value = "delete fraud ip list by id", type = OperationTypeEnum.DELETE)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_DELETE + "')")
//    @ApiOperation(value = "ID删除")
//    @DeleteMapping(value = "{id}")
//    public Response delete(@PathVariable Long id) {
//        riskIpListService.delete(id);
//        return Response.success();
//    }
    @AuditLog(value = "delete fraud ip list by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_DELETE + "')")
    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        RiskIpList riskIpList = riskIpListService.queryById(id);
        Approved approved = Approved.builder()
                .identifier(riskIpList.getCountry()+"&"+riskIpList.getStart())
                .function(ApprovedFunctionEnum.RISK_MANAGE_IP_CONFIG_DELETE)
                .beforeData(JSONObject.toJSONString(riskIpList))
                .afterData(""+id)
                .build();
        approvedService.insert(approved);
        return Response.success();
    }

    @AuditLog(value = "delete fraud ip list by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_APPROVAL + "')")
    @ApiOperation(value = "ID删除")
    @PutMapping(value = "delete/approval")
    @Transactional
    public Response delete(@Valid @RequestBody ApprovedRO approvedRO) {
        Approved approved = approvedService.approval(approvedRO);
        if (ApprovedStatusEnum.APPROVED.equals(approvedRO.getStatus())) {
            Long id = Long.valueOf(approved.getAfterData());
            riskIpListService.delete(id);
            riskIpListService.updateCache();
        }
        approvedService.sendEmail(approved);
        return Response.success();
    }

    @AuditLog(value = "query fraud ip list by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskIpListService.queryById(id));
    }

    @AuditLog(value = "query fraud ip list by all", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SEARCH + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskIpListService.getAll());
    }

    @AuditLog(value = "query fraud ip list as paginated", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskIpListQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskIpListMapper.selectByAll(mapper.map(qro, RiskIpList.class)));
        return Response.success(pageInfo);
    }

}
