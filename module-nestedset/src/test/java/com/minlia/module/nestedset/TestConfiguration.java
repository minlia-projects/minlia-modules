package com.minlia.module.nestedset;

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

import com.google.common.collect.Lists;
import com.minlia.module.nestedset.config.jdbc.discriminator.TestJdbcTreeDiscriminator;
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.config.jpa.discriminator.TestJpaTreeDiscriminator;
import com.minlia.module.nestedset.config.jpa.factory.JpaNestedSetRepositoryFactory;
import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.config.mem.discriminator.TestInMemoryTreeDiscriminator;
import com.minlia.module.nestedset.delegate.query.jdbc.JdbcKeyHolder;
import com.minlia.module.nestedset.model.TestSet;
import com.minlia.module.nestedset.qualifier.Jdbc;
import com.minlia.module.nestedset.qualifier.Jpa;
import com.minlia.module.nestedset.qualifier.Mem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.config.jdbc.factory.JdbcNestedNodeRepositoryFactory;
import com.minlia.module.nestedset.config.mem.factory.InMemoryNestedNodeRepositoryFactory;
import com.minlia.module.nestedset.config.mem.lock.InMemoryLock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
public class TestConfiguration {

    @PersistenceContext
    EntityManager entityManager;

    private static final AtomicLong ID = new AtomicLong();

    public static final List<TestSet> IN_MEM_NODES = Lists.newArrayList(
            new TestSet(1000L, 1L, 0L, 16L, "a", null, "tree_1"),
            new TestSet(2000L, 2L, 1L, 7L, "b", 1000L, "tree_1"),
            new TestSet(3000L, 8L, 1L, 15L, "c", 1000L, "tree_1"),
            new TestSet(4000L, 3L, 2L, 4L, "d", 2000L, "tree_1"),
            new TestSet(5000L, 5L, 2L, 6L, "e", 2000L, "tree_1"),
            new TestSet(6000L, 9L, 2L, 10L, "f", 3000L, "tree_1"),
            new TestSet(7000L, 11L, 2L, 14L, "g", 3000L, "tree_1"),
            new TestSet(8000L, 12L, 3L, 13L, "h", 7000L, "tree_1"),
            new TestSet(9000L, 1L, 0L, 16L, "a2", null, "tree_2"),
            new TestSet(10000L, 2L, 1L, 7L, "b2", 9000L, "tree_2"),
            new TestSet(11000L, 8L, 1L, 15L, "c2", 9000L, "tree_2"),
            new TestSet(12000L, 3L, 2L, 4L, "d2", 10000L, "tree_2"),
            new TestSet(13000L, 5L, 2L, 6L, "e2", 10000L, "tree_2"),
            new TestSet(14000L, 9L, 2L, 10L, "f2", 11000L, "tree_2"),
            new TestSet(15000L, 11L, 2L, 14L, "g2", 11000L, "tree_2"),
            new TestSet(16000L, 12L, 3L, 13L, "h2", 15000L, "tree_2")
    );

    public static final InMemoryNestedNodeRepositoryConfiguration<Long, TestSet> IN_MEM_CONFIG = inMemoryConfiguration();

//    @Bean
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName(UUID.randomUUID().toString());
//        return builder.build();
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        ds.setDriverClassName(net.sf.log4jdbc.DriverSpy.class.getName());
        ds.setUrl("jdbc:log4jdbc:mysql://localhost:3306/tudou?createDatabaseIfNotExist=true&useUnicode=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&verifyServerCertificate=false&useSSL=false&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&allowMultiQueries=true&serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }

    @Bean
    public Properties additionalProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.transaction.flush_before_completion", "false");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");
        properties.setProperty("hibernate.hbm2ddl.import_files", "/fixtures/test-import.sql");
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties additionalProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.minlia.module.nestedset");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Jpa
    public NestedNodeRepository<Long, TestSet> jpaRepository() {
        JpaNestedSetRepositoryConfiguration<Long, TestSet> configuration = new JpaNestedSetRepositoryConfiguration<>(
                entityManager, TestSet.class, Long.class, new TestJpaTreeDiscriminator()
        );
        return JpaNestedSetRepositoryFactory.create(configuration);
    }

    @Bean
    @Jdbc
    public NestedNodeRepository<Long, TestSet> jdbcRepository(DataSource dataSource) {
        //ROW MAPPER FOR CREATING INSTANCES OF THE NODE OBJECT
        RowMapper<TestSet> mapper = (resultSet, i) -> TestSet.fromResultSet(resultSet);

        //TABLE NAME
        String tableName = "nested_nodes";

        // QUERY USED FOR INSERTING NEW NODES
        String insertQuery = "insert into nested_nodes(id, tree_left, tree_level, tree_right, node_name, parent_id, discriminator) values(next value for SEQ,?,?,?,?,?,?)";

        // INSERT QUERY VALUES PROVIDER, CONVERTS NODE OBJECT INTO AN OBJECT ARRAY
        Function<TestSet, Object[]> insertValuesProvider = n -> new Object[]{n.getLeft(), n.getLevel(), n.getRight(), n.getName(), n.getParentId(), n.getDiscriminator()};

        // METHOD OF RETRIEVING GENERATED DATABASE PRIMARY KEYS
        BiFunction<TestSet, JdbcKeyHolder, Long> generatedKeyResolver = (node, jdbcKeyHolder) -> jdbcKeyHolder.getKeyValueAs(Long.class);

        JdbcNestedNodeRepositoryConfiguration<Long, TestSet> configuration = new JdbcNestedNodeRepositoryConfiguration<>(
                new JdbcTemplate(dataSource), tableName, mapper, insertQuery, insertValuesProvider, generatedKeyResolver, new TestJdbcTreeDiscriminator()
        );

        configuration.setIdColumnName("id");
        configuration.setParentIdColumnName("parent_id");
        configuration.setLeftColumnName("tree_left");
        configuration.setRightColumnName("tree_right");
        configuration.setLevelColumnName("tree_level");

        return JdbcNestedNodeRepositoryFactory.create(configuration);
    }

    @Bean
    @Mem
    public NestedNodeRepository<Long, TestSet> inMemoryRepository() {
        return InMemoryNestedNodeRepositoryFactory.create(IN_MEM_CONFIG, new InMemoryLock<>(TestSet::getDiscriminator));
    }

    private static InMemoryNestedNodeRepositoryConfiguration<Long, TestSet> inMemoryConfiguration() {
        return new InMemoryNestedNodeRepositoryConfiguration<>(
                ID::incrementAndGet, IN_MEM_NODES, new TestInMemoryTreeDiscriminator()
        );
    }

}
