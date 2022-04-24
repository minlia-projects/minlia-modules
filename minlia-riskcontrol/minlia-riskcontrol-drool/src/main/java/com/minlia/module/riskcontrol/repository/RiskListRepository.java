package com.minlia.module.riskcontrol.repository;

import com.minlia.module.riskcontrol.entity.RiskList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/6/26 5:07 PM
 */
@Repository
public interface RiskListRepository extends JpaRepository<RiskList, Long> {

    /**
     * @param type
     * @param dimension
     * @param value
     * @return
     */
    boolean existsByTypeAndDimensionAndValue(RiskTypeEnum type, RiskList.EnumDimension dimension, String value);

    /**
     * 根据类型、纬度查询
     *
     * @param type
     * @param dimension
     * @return
     */
    List<RiskList> findByTypeAndDimension(RiskTypeEnum type, RiskList.EnumDimension dimension);

}