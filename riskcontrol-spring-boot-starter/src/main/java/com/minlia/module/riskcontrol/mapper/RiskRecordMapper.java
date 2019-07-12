package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.bean.RiskRecordQRO;
import com.minlia.module.riskcontrol.entity.RiskRecord;

import java.util.List;

public interface RiskRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(RiskRecord record);

    RiskRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskRecord record);

    List<RiskRecord> selectByAll(RiskRecordQRO qro);

}