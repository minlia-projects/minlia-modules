package com.minlia.module.approved.service;

import com.minlia.module.approved.bean.ro.ApprovedQRO;
import com.minlia.module.approved.bean.ro.ApprovedRO;
import com.minlia.module.approved.bean.vo.ApprovedVO;
import com.minlia.module.approved.entity.Approved;

import java.util.List;

public interface ApprovedService{

    int deleteByPrimaryKey(Long id);

    int insert(Approved record);

    int insertSelective(Approved record);

    Approved selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Approved record);

    int updateByPrimaryKey(Approved record);

//    List<ApprovedVO> builderDetailsParams(List<Approved> approveds);

    Approved selectOneByAll(ApprovedQRO qro);

    ApprovedVO selectDetailsOneByAll(ApprovedQRO qro);

    List<ApprovedVO> selectDetailsByAll(ApprovedQRO qro);

    List<Approved> selectByAll(ApprovedQRO qro);

    Approved approval(ApprovedRO approvedRO);

    void sendEmail(Approved approved);
}
