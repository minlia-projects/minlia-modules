
package com.minlia.module.nestedset.delegate.query.jdbc;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia, Inc
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

import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetInsertingQueryDelegate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.Types;

public class JdbcNestedSetInsertingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JdbcNestedSetQueryDelegate<ID, N>
        implements NestedSetInsertingQueryDelegate<ID, N> {

    public JdbcNestedSetInsertingQueryDelegate(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }

    @Override
    public Class<?> getClz() {
        return null;
    }

    @Override

    public void insert(N entity) {
        if (entity.getId() == null) {
            doInsert(entity);
        } else {
            update(entity);
        }

    }

    private void update(N node) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :left = ?, :right = ?, :level = ?, :parentId = ? where :id = ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getLeft());
                    preparedStatement.setObject(2, node.getRight());
                    preparedStatement.setObject(3, node.getLevel());
                    if (node.getParentId() == null) {
                        preparedStatement.setNull(4, Types.OTHER);
                    } else {
                        preparedStatement.setObject(4, node.getParentId());
                    }
                    preparedStatement.setObject(5, node.getId());
                    setDiscriminatorParams(preparedStatement, 6);
                }
        );
    }

    private void doInsert(N node) {
        JdbcKeyHolder keyHolder = new JdbcKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertQuery, new String[]{id});
            Object[] params = insertValuesProvider.apply(node);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps;
        }, keyHolder);
        node.setId(generatedKeyResolver.apply(node, keyHolder));
    }

    @Override
    public void incrementSideFieldsGreaterThan(Long from, String fieldName) {
        updateFields(from, fieldName, false);
    }

    @Override
    public void incermentSideFieldsGreaterThanOrEqualTo(Long from, String fieldName) {
        updateFields(from, fieldName, true);
    }

    private void updateFields(Long from, String fieldName, boolean gte) {
        String columnName = treeColumnNames.get(fieldName);
        String sign = gte ? ">=" : ">";
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :columnName = (:columnName + ?) where :columnName :sign ?")
                                .set("columnName", columnName)
                                .set("sign", sign)
                                .build()
                ),
                preparedStatement -> {
                    preparedStatement.setLong(1, INCREMENT_BY);
                    preparedStatement.setLong(2, from);
                    setDiscriminatorParams(preparedStatement, 3);
                }
        );
    }
}
