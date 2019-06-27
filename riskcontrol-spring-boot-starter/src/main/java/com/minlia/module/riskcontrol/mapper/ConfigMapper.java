package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.entity.RiskConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Mapper
public interface ConfigMapper {

    @Select("select * from CONFIG")
    List<RiskConfig> queryAll();

    @Update("update CONFIG set value=#{value} WHERE `key`=#{key}")
    int update(RiskConfig riskConfig);

}