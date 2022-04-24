package com.minlia.module.riskcontrol.repository;

import com.minlia.module.riskcontrol.entity.RiskDrools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author garen
 */
@Repository
public interface RiskDroolsRepository extends JpaRepository<RiskDrools, Long> {

}