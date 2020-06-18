package com.minlia.app.demo.nestedset;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia Team
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
