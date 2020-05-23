package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.entity.RiskBlackUrl;

import java.util.List;

public interface RiskBlackUrlMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(RiskBlackUrl record);

    RiskBlackUrl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskBlackUrl record);

    List<RiskBlackUrl> selectByAll(RiskBlackUrl riskBlackUrl);

}