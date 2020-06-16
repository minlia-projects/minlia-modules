package com.minlia.app.demo.nestedset;

import com.minlia.app.demo.nestedset.DemoJpaTreeDiscriminator;
import com.minlia.app.demo.nestedset.entity.Team;
import com.minlia.module.nestedset.NestedNodeRepository;
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.config.jpa.factory.JpaNestedSetRepositoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class NestedSetManagerAutoConfiguration {

    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public NestedNodeRepository<Long, Team> categoryNestedSetRepository() {
        JpaNestedSetRepositoryConfiguration<Long, Team> configuration = new JpaNestedSetRepositoryConfiguration<>(
                entityManager, Team.class, Long.class, new DemoJpaTreeDiscriminator()
        );

        return JpaNestedSetRepositoryFactory.create(configuration);
    }
}
