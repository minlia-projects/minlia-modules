package com.minlia.module.drools.repository;

import com.minlia.module.drools.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    Rule findByRuleKey(String ruleKey);

}