package com.minlia.app.demo.nestedset;

import com.minlia.app.demo.nestedset.entity.Team;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.minlia.module.nestedset.NestedNodeRepository;
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.config.jpa.factory.JpaNestedSetRepositoryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableJpaRepositories
@EntityScan(basePackages = "**.entity.**")
public class DemoAppConfig {

    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public NestedNodeRepository<Long, Team> jpaRepository() {

        //define configuration attributes
        JpaNestedSetRepositoryConfiguration<Long, Team> configuration = new JpaNestedSetRepositoryConfiguration<>(
                entityManager, Team.class, Long.class, new DemoJpaTreeDiscriminator()
        );

        //create repository from configuration
        return JpaNestedSetRepositoryFactory.create(configuration);
    }

}
