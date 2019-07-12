package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.entity.RiskIpList;

import java.util.List;

public interface RiskIpListMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(RiskIpList record);

    RiskIpList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskIpList record);

    List<RiskIpList> selectByAll(RiskIpList riskIpList);

}