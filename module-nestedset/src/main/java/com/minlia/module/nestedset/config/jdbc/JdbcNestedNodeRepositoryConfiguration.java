
package com.minlia.module.nestedset.config.jdbc;

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
import com.google.common.collect.Maps;
import com.minlia.module.nestedset.config.jdbc.discriminator.JdbcTreeDiscriminator;
import com.minlia.module.nestedset.delegate.query.jdbc.JdbcKeyHolder;
import com.minlia.module.nestedset.model.NestedSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Configuration class that serves as a base of creating new instances of JDBC Repository.
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public class JdbcNestedNodeRepositoryConfiguration<ID extends Serializable, N extends NestedSet<ID>> {

    private final JdbcTemplate jdbcTemplate;

    private final String tableName;

    private final RowMapper<N> rowMapper;

    private final String insertQuery;

    private final Function<N, Object[]> insertValuesProvider;

    private final BiFunction<N, JdbcKeyHolder, ID> generatedKeyResolver;

    private final JdbcTreeDiscriminator treeDiscriminator;

    private String selectQuery;

    private Map<String, String> treeColumnNames = Maps.newHashMap();

    /**
     * Creates new JDBC Repository with custm Tree Discriminator.
     *  @param jdbcTemplate - Spring JDBC Template to be used by the Repository
     * @param tableName - name of the Database Table with Nested Nodes
     * @param rowMapper - Spring RowMapper that can create instances of N from SQL ResultSet
     * @param insertQuery - SQL Query used to insert the Nodes to the Table
     * @param insertValuesProvider - provider of Insert SQL values
     * @param generatedKeyResolver - generated db keys resolver
     * @param treeDiscriminator - custom Tree Discriminator
     */
    public JdbcNestedNodeRepositoryConfiguration(JdbcTemplate jdbcTemplate, String tableName,
                                                 RowMapper<N> rowMapper, String insertQuery,
                                                 Function<N, Object[]> insertValuesProvider,
                                                 BiFunction<N, JdbcKeyHolder, ID> generatedKeyResolver,
                                                 JdbcTreeDiscriminator treeDiscriminator) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.rowMapper = rowMapper;
        this.insertQuery = insertQuery;
        this.insertValuesProvider = insertValuesProvider;
        this.generatedKeyResolver = generatedKeyResolver;
        this.treeDiscriminator = treeDiscriminator;
        treeColumnNames.put(NestedSet.ID, NestedSet.ID);
        treeColumnNames.put(NestedSet.LEFT, NestedSet.LEFT);
        treeColumnNames.put(NestedSet.RIGHT, NestedSet.RIGHT);
        treeColumnNames.put(NestedSet.LEVEL, NestedSet.LEVEL);
        treeColumnNames.put(NestedSet.PARENT_ID, NestedSet.PARENT_ID);
        this.selectQuery = String.format("select * from %s", tableName);
    }

    /**
     * Creates new JDBC Repository with no Tree Discriminator.
     *  @param jdbcTemplate - Spring JDBC Template to be used by the Repository
     * @param tableName - name of the Database Table with Nested Nodes
     * @param rowMapper - Spring RowMapper that can create instances of N from SQL ResultSet
     * @param insertQuery - SQL Query used to insert the Nodes to the Table
     * @param insertValuesProvider - provider of Insert SQL values
     * @param generatedKeyResolver - generated db keys resolver
     */
    public JdbcNestedNodeRepositoryConfiguration(JdbcTemplate jdbcTemplate, String tableName,
                                                 RowMapper<N> rowMapper, String insertQuery,
                                                 Function<N, Object[]> insertValuesProvider,
                                                 BiFunction<N, JdbcKeyHolder, ID> generatedKeyResolver) {
        this(jdbcTemplate, tableName, rowMapper, insertQuery, insertValuesProvider, generatedKeyResolver, new JdbcTreeDiscriminator() {
            @Override
            public String getQueryPart() {
                return "";
            }

            @Override
            public List<Object> getParameters() {
                return Lists.newLinkedList();
            }
        });
    }

    /**
     * @return Spring JdbcTemplate used by this Configuration
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @return Database Table name used by this Configuration
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return Spring RowMapper used by this Configuration
     */
    public RowMapper<N> getRowMapper() {
        return rowMapper;
    }

    /**
     * @return Insert SQL Query used by this Configuration
     */
    public String getInsertQuery() {
        return insertQuery;
    }

    /**
     * @return Insert statement values provider used by this Configuration
     */
    public Function<N, Object[]> getInsertValuesProvider() {
        return insertValuesProvider;
    }

    /**
     * @return Tree Discriminator used by this Configuration
     */
    public JdbcTreeDiscriminator getTreeDiscriminator() {
        return treeDiscriminator;
    }

    /**
     * @return Maping of SQL column names to standarized field names used by this Configuration
     */
    public Map<String, String> getTreeColumnNames() {
        return treeColumnNames;
    }

    /**
     * Sets custom ID column name
     */
    public JdbcNestedNodeRepositoryConfiguration<ID, N> setIdColumnName(String field) {
        treeColumnNames.put(NestedSet.ID, field);
        return this;
    }

    /**
     * Sets custom PARENT_ID column name
     */
    public JdbcNestedNodeRepositoryConfiguration<ID, N> setParentIdColumnName(String field) {
        treeColumnNames.put(NestedSet.PARENT_ID, field);
        return this;
    }

    /**
     * Sets custom LEFT column name
     */
    public JdbcNestedNodeRepositoryConfiguration<ID, N> setLeftColumnName(String field) {
        treeColumnNames.put(NestedSet.LEFT, field);
        return this;
    }

    /**
     * Sets custom RIGHT name
     */
    public JdbcNestedNodeRepositoryConfiguration<ID, N> setRightColumnName(String field) {
        treeColumnNames.put(NestedSet.RIGHT, field);
        return this;
    }

    /**
     * Sets custom LEVEL column name
     */
    public JdbcNestedNodeRepositoryConfiguration<ID, N> setLevelColumnName(String field) {
        treeColumnNames.put(NestedSet.LEVEL, field);
        return this;
    }

    /**
     * @return SQL Select Query used by this Configuration
     */
    public String getSelectQuery() {
        return selectQuery;
    }

    /**
     * @return JDBC Generated Keys Resolver
     */
    public BiFunction<N, JdbcKeyHolder, ID> getGeneratedKeyResolver() {
        return generatedKeyResolver;
    }

    /**
     * Sets custom SQL Select query
     */
    public void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }
}
