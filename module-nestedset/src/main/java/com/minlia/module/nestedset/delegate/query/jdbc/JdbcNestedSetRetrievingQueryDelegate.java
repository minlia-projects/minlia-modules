
package com.minlia.module.nestedset.delegate.query.jdbc;

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
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class JdbcNestedSetRetrievingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JdbcNestedSetQueryDelegate<ID, N>
        implements NestedSetRetrievingQueryDelegate<ID, N> {

    public JdbcNestedSetRetrievingQueryDelegate(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public List<N> getTreeAsList(N entity) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :left >= ? and :right <= ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, entity.getLeft());
                    preparedStatement.setObject(2, entity.getRight());
                    setDiscriminatorParams(preparedStatement, 3);
                },
                rowMapper
        );
    }

    @Override
    public List<N> getChildren(N entity) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :left >= ? and :right <= ? and :level = ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, entity.getLeft());
                    preparedStatement.setObject(2, entity.getRight());
                    preparedStatement.setObject(3, entity.getLevel() + 1);
                    setDiscriminatorParams(preparedStatement, 4);
                },
                rowMapper
        );
    }

    @Override
    public Optional<N> getParent(N entity) {
        if (entity.getLevel() > 0) {
            return jdbcTemplate.query(
                    getDiscriminatedQuery(
                            new Query("select * from :tableName where :left < ? and :right > ? and :level = ? order by :left asc").build()
                    ),
                    preparedStatement -> {
                        preparedStatement.setObject(1, entity.getLeft());
                        preparedStatement.setObject(2, entity.getRight());
                        preparedStatement.setObject(3, entity.getLevel() - 1);
                        setDiscriminatorParams(preparedStatement, 4);
                    },
                    rowMapper
            ).stream().findFirst();
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<N> getParents(N entity) {
        if (entity.getLevel() > 0) {
            return jdbcTemplate.query(
                    getDiscriminatedQuery(
                            new Query("select * from :tableName where :left < ? and :right > ? order by :left desc").build()
                    ),
                    preparedStatement -> {
                        preparedStatement.setObject(1, entity.getLeft());
                        preparedStatement.setObject(2, entity.getRight());
                        setDiscriminatorParams(preparedStatement, 3);
                    },
                    rowMapper
            );
        } else {
            return Lists.newLinkedList();
        }
    }

    @Override
    public Optional<N> getPrevSibling(N entity) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :right = ? and :level = ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, entity.getLeft() - 1);
                    preparedStatement.setObject(2, entity.getLevel());
                    setDiscriminatorParams(preparedStatement, 3);
                },
                rowMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<N> getNextSibling(N entity) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :left = ? and :level = ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, entity.getRight() + 1);
                    preparedStatement.setObject(2, entity.getLevel());
                    setDiscriminatorParams(preparedStatement, 3);
                },
                rowMapper
        ).stream().findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<NestedSetDetail<ID>> getNestedSetDetail(ID nodeId) {
        NestedSetDetail<ID> info = jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select :id, :parentId, :left, :right, :level from :tableName where :id = ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, nodeId);
                    setDiscriminatorParams(preparedStatement, 2);
                },
                (ResultSetExtractor<NestedSetDetail<ID>>) rs -> {
                    if(!rs.next()) {
                        return null;
                    }
                    return new NestedSetDetail((ID) rs.getObject(id), (ID) rs.getObject(parentId), rs.getLong(left), rs.getLong(right), rs.getLong(level));
                }
        );
        return Optional.ofNullable(info);
    }

    @Override
    public Optional<N> findFirstRoot() {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :level = 0 order by :left asc").build()
                ),
                preparedStatement -> setDiscriminatorParams(preparedStatement, 1),
                rowMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<N> findLastRoot() {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :level = 0 order by :left desc").build()
                ),
                preparedStatement -> setDiscriminatorParams(preparedStatement, 1),
                rowMapper
        ).stream().findFirst();
    }
}
