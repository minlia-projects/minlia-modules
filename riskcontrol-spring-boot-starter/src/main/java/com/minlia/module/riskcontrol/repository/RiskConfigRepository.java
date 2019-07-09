package com.minlia.module.riskcontrol.repository;

import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
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
public interface RiskConfigRepository extends JpaRepository<RiskDroolsConfig, Long> {

    boolean existsAllByRuleKey(String ruleKey);

    List<RiskDroolsConfig> findAllByDisFlag(boolean disFlag);

    RiskDroolsConfig findOneByRuleKey(String ruleKey);

}