package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RiskDroolsConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(RiskDroolsConfig record);

    RiskDroolsConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskDroolsConfig record);

    List<RiskDroolsConfig> queryByAll(RiskDroolsConfig riskDroolsConfig);



//    @Insert("insert into BLACK_LIST(dimension,type,value,detail) VALUES (#{dimension},#{type},#{value},#{detail})")
//    int add(RiskBlackList riskBlackList);
//
//    @Select("select * from mdl_risk_drools_config")
//    List<RiskDroolsConfig> queryAll(RiskDroolsConfig riskDroolsConfig);
}