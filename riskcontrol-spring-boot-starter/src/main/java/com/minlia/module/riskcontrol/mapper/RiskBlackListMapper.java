package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.entity.RiskBlackList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RiskBlackListMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(RiskBlackList record);

    RiskBlackList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskBlackList record);

    List<RiskBlackList> selectByAll(RiskBlackList riskBlackList);

}