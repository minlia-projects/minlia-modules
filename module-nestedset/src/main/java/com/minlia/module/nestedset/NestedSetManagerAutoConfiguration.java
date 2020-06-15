package com.minlia.module.nestedset;

import com.minlia.module.nestedset.node.JpaNestedSetManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class NestedSetManagerAutoConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @ConditionalOnMissingBean(value = JpaNestedSetManager.class)
    public JpaNestedSetManager jpaNestedSetManager() {
        return new JpaNestedSetManager(entityManager);
    }

}
