package com.minlia.module.approved.service.impl;

import com.google.common.collect.Maps;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.approved.bean.ro.ApprovedQRO;
import com.minlia.module.approved.bean.ro.ApprovedRO;
import com.minlia.module.approved.bean.vo.ApprovedVO;
import com.minlia.module.approved.email.service.ApprovedEmailService;
import com.minlia.module.approved.entity.Approved;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.approved.enumeration.ApprovedVrifyMessage;
import com.minlia.module.approved.mapper.ApprovedMapper;
import com.minlia.module.approved.service.ApprovedService;
import com.minlia.module.data.context.UserPrincipalHolder;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovedServiceImpl implements ApprovedService {

    @Resource
    private ApprovedMapper approvedMapper;
    @Autowired
    private ApprovedEmailService approvedEmailService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return approvedMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Approved approved) {
        ApiAssert.isNull(approvedMapper.selectOneByAll(ApprovedQRO.builder().function(approved.getFunction()).identifier(approved.getIdentifier()).status(ApprovedStatusEnum.PENDING).build()),ApprovedVrifyMessage.TASK_EXISTS);
        approved.setStatus(ApprovedStatusEnum.PENDING);
        approved.setRequestDateTime(LocalDateTime.now());
        approved.setRequestorUserGuid(UserPrincipalHolder.getCurrentUserLogin());
        approvedEmailService.sendRichtextMail("ADMIN",approvedMapper.selectApprovedUserEmails(),"JETCO_APPROVER_SUBMITTED", Maps.newHashMap());
        return approvedMapper.insert(approved);
    }

    @Override
    public int insertSelective(Approved record) {
        return approvedMapper.insertSelective(record);
    }

    @Override
    public Approved selectByPrimaryKey(Long id) {
        return approvedMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Approved record) {
        return approvedMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Approved record) {
        return approvedMapper.updateByPrimaryKey(record);
    }

    @Override
    public Approved selectOneByAll(ApprovedQRO qro) {
        return approvedMapper.selectOneByAll(qro);
    }

    @Override
    public ApprovedVO selectDetailsOneByAll(ApprovedQRO qro) {
        return approvedMapper.selectDetailsOneByAll(qro);
    }

    @Override
    public List<ApprovedVO> selectDetailsByAll(ApprovedQRO qro) {
        return approvedMapper.selectDetailsByAll(qro);
    }

    @Override
    public List<Approved> selectByAll(ApprovedQRO qro) {
        return approvedMapper.selectByAll(qro);
    }

    @Override
    public Approved approval(ApprovedRO approvedRO) {
        Boolean isApprover = MinliaSecurityContextHolder.getUserContext().getIsApprover();
        ApiAssert.state(isApprover, ApprovedVrifyMessage.NOT_APPROVED_PERMISSION);
        String currentUser = MinliaSecurityContextHolder.getUserContext().getGuid();
//        ApiAssert.notNull(currentUser,"");
        Approved approved = this.selectByPrimaryKey(approvedRO.getId());
        ApiAssert.notNull(approved, ApprovedVrifyMessage.TASK_NOT_EXISTS);
        ApiAssert.state(ApprovedStatusEnum.PENDING.equals(approved.getStatus()),ApprovedVrifyMessage.TASK_NOT_EXISTS);
        ApiAssert.state(!currentUser.equals(approved.getRequestorUserGuid()),ApprovedVrifyMessage.TASK_NOT_MYSELF);

        approved.setStatus(approvedRO.getStatus());
        approved.setApproverUserGuid(currentUser);
        approved.setApprovedDateTime(LocalDateTime.now());
        approved.setRemark(approvedRO.getRemark());
        this.updateByPrimaryKeySelective(approved);
        return approved;
    }

    @Override
    public void sendEmail(Approved approved){
        String templateCode = null;
        if(ApprovedStatusEnum.APPROVED.equals(approved.getStatus())){
            templateCode = "JETCO_APPROVER_AGREE";
        }else if(ApprovedStatusEnum.REJECTED.equals(approved.getStatus())){
            templateCode = "JETCO_APPROVER_REJECT";
        }else{
            return;
        }
        String guid = approvedMapper.selectEmailByGuid(approved.getRequestorUserGuid());
        if(StringUtils.isNotBlank(guid)){
            approvedEmailService.sendRichtextMail("ADMIN",new String[]{approvedMapper.selectEmailByGuid(approved.getRequestorUserGuid())},templateCode, Maps.newHashMap());
        }
    }
//    /**
//     * 封装返回给页面展示的值
//     */
//    private ApprovedVO builderDetailsParams(Approved approved) {
//        if (null == approved) {
//            return null;
//        }
//        ApprovedVO vo = mapper.map(approved, ApprovedVO.class);
////        if (null != vo.getGender()) {
////            vo.setGenderVal(lovValueService.selectNameByCodeAndLovCode(ApplicationLovConstants.GENDER, vo.getGender().name()));
////        }
//        return vo;
//    }
//
//    /**
//     * 封装返回给页面展示的值
//     */
//    @Override
//    public List<ApprovedVO> builderDetailsParams(List<Approved> approveds) {
//        List<ApprovedVO> list = Lists.newArrayList();
//        if (CollectionUtils.isNotEmpty(approveds)) {
//            approveds.stream().forEach(approved -> list.add(this.builderDetailsParams(approved)));
//        }
//        return list;
//    }
}
