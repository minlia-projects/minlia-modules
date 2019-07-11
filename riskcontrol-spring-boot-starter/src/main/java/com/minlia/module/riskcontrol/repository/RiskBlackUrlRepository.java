package com.minlia.module.riskcontrol.repository;

import com.minlia.module.riskcontrol.entity.RiskBlackUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/6/26 5:07 PM
 */
@Repository
public interface RiskBlackUrlRepository extends JpaRepository<RiskBlackUrl, Long> {

    long countAllByTypeAndValue(RiskBlackUrl.EnumType type, String value);

    boolean existsAllByTypeAndValue(RiskBlackUrl.EnumType type, String value);

}
