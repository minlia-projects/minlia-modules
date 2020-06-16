
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
import com.minlia.module.nestedset.delegate.query.NestedSetRebuildingQueryDelegate;

import java.io.Serializable;
import java.util.List;

public class JdbcNestedSetRebuildingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JdbcNestedSetQueryDelegate<ID, N>
        implements NestedSetRebuildingQueryDelegate<ID, N> {

    public JdbcNestedSetRebuildingQueryDelegate(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public void destroyTree() {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :left = 0, :right = 0, :level = 0").build()
                ),
                preparedStatement -> setDiscriminatorParams(preparedStatement, 1)
        );
    }

    @Override
    public N findFirst() {
        List<N> result =  jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :parentId is null order by :id desc").build()
                ),
                preparedStatement -> {
                    setDiscriminatorParams(preparedStatement, 1);
                },
                rowMapper
        );
        return result.stream().findFirst().orElse(null);
    }

    @Override
    public void resetFirst(N first) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :left = 1, :right = 2, :level = 0 where :id = ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, first.getId());
                    setDiscriminatorParams(preparedStatement, 2);
                }
        );
    }

    @Override
    public List<N> getSiblings(ID first) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :parentId is null and :id <> ? order by :id asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, first);
                    setDiscriminatorParams(preparedStatement, 2);
                },
                rowMapper
        );
    }

    @Override
    public List<N> getChildren(N parent) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :parentId = ? order by :id asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, parent.getId());
                    setDiscriminatorParams(preparedStatement, 2);
                },
                rowMapper
        );
    }
}
