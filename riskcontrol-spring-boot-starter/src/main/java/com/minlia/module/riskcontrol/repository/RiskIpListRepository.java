package com.minlia.module.riskcontrol.repository;

import com.minlia.module.riskcontrol.entity.RiskIpList;
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
public interface RiskIpListRepository extends JpaRepository<RiskIpList, Long> {

    List<RiskIpList> findAllByDisFlag(boolean disFlag);

    boolean existsAllByCountryAndStart(RiskIpList.EnumCountry country, Long start);

}
