package com.minlia.module.riskcontrol.repository;

import com.minlia.module.riskcontrol.entity.RiskConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/6/26 5:07 PM
 */
@Repository
public interface RiskConfigRepository extends JpaRepository<RiskConfig, Long> {

}