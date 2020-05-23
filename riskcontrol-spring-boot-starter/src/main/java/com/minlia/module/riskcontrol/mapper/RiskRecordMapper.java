package com.minlia.module.riskcontrol.mapper;

import com.minlia.module.riskcontrol.bean.RiskRecordQRO;
import com.minlia.module.riskcontrol.entity.RiskRecord;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RiskRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(RiskRecord record);

    RiskRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskRecord record);

    List<RiskRecord> selectByAll(RiskRecordQRO qro);

    List<RiskRecord> selectMaxCountBySceneAndLevelAndDateTimeAndGroupBySceneValue(@Param("scene") String scene, @Param("level") RiskLevelEnum level, @Param("date") LocalDate date);

    List<RiskRecord> selectMaxCountBySceneAndLevelAndDateTimeAndGroupByGuid(@Param("scene") String scene, @Param("level") RiskLevelEnum level, @Param("date") LocalDate date);

}