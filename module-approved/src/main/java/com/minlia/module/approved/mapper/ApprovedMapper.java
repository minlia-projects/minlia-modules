package com.minlia.module.approved.mapper;

import com.minlia.module.approved.bean.ro.ApprovedQRO;
import com.minlia.module.approved.bean.vo.ApprovedVO;
import com.minlia.module.approved.entity.Approved;

import java.util.List;

public interface ApprovedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Approved record);

    int insertSelective(Approved record);

    Approved selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Approved record);

    int updateByPrimaryKey(Approved record);

    List<Approved> selectByAll(ApprovedQRO qro);

    Approved selectOneByAll(ApprovedQRO qro);

    List<ApprovedVO> selectDetailsByAll(ApprovedQRO qro);

    ApprovedVO selectDetailsOneByAll(ApprovedQRO qro);

    String[] selectApprovedUserEmails();

    String selectEmailByGuid(String guid);
}