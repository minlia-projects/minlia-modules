
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

import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetRemovingQueryDelegate;

import java.io.Serializable;
import java.sql.Types;
import java.util.Optional;

public class JdbcNestedSetRemovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JdbcNestedSetQueryDelegate<ID, N>
        implements NestedSetRemovingQueryDelegate<ID, N> {

    public JdbcNestedSetRemovingQueryDelegate(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public Class<?> getClz() {
        return null;
    }

    @Override
    public void setNewParentForDeletedNodesChildren(NestedSetDetail<ID> nestedSetDetail) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :parentId = ? where :left >= ? and :right <= ? and :level = ?").build()
                ),
                preparedStatement -> {
                    Optional<ID> newParentId = findNodeParentId(nestedSetDetail);
                    if (!newParentId.isPresent()) {
                        preparedStatement.setNull(1, Types.OTHER);
                    } else {
                        preparedStatement.setObject(1, newParentId.get());
                    }
                    preparedStatement.setLong(2, nestedSetDetail.getLeft());
                    preparedStatement.setLong(3, nestedSetDetail.getRight());
                    preparedStatement.setLong(4, nestedSetDetail.getLevel() + 1);
                    setDiscriminatorParams(preparedStatement, 5);
                }
        );
    }

    @SuppressWarnings("unchecked")
    private Optional<ID> findNodeParentId(NestedSetDetail<ID> node) {
        ID id = null;
        if (node.getLevel() > 0) {
            id = jdbcTemplate.query(
                    getDiscriminatedQuery(
                            new Query("select :id from :tableName where :left < ? and :right > ? and :level = ?").build()
                    ),
                    preparedStatement -> {
                        preparedStatement.setLong(1, node.getLeft());
                        preparedStatement.setLong(2, node.getRight());
                        preparedStatement.setLong(3, node.getLevel() - 1);
                        setDiscriminatorParams(preparedStatement, 4);
                    },
                    rs -> {
                        if (!rs.next()) {
                            throw new InvalidNodeException(String.format("Couldn't find node's parent, although its level is greater than 0. It seems the tree is malformed: %s", node));
                        }
                        return (ID) rs.getObject(this.id);
                    }
            );
        }
        return Optional.ofNullable(id);
    }

    @Override
    public void performSingleDeletion(NestedSetDetail<ID> nestedSetDetail) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("delete from :tableName where :id = ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, nestedSetDetail.getId());
                    setDiscriminatorParams(preparedStatement, 2);
                }
        );
    }

    @Override
    public void decrementSideFieldsBeforeSingleNodeRemoval(Long from, String field) {
        decrementSideFields(from, DECREMENT_BY, field);
    }

    @Override
    public void pushUpDeletedNodesChildren(NestedSetDetail<ID> nestedSetDetail) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :right = (:right - 1), :left = (:left - 1), :level = (:level - 1) where :right < ? and :left > ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, nestedSetDetail.getRight());
                    preparedStatement.setObject(2, nestedSetDetail.getLeft());
                    setDiscriminatorParams(preparedStatement, 3);
                }
        );
    }

    @Override
    public void decrementSideFieldsAfterSubtreeRemoval(Long from, Long delta, String field) {
        decrementSideFields(from, delta, field);
    }

    @Override
    public void performBatchDeletion(NestedSetDetail<ID> nestedSetDetail) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("delete from :tableName where :left >= ? and :right <= ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, nestedSetDetail.getLeft());
                    preparedStatement.setObject(2, nestedSetDetail.getRight());
                    setDiscriminatorParams(preparedStatement, 3);
                }
        );
    }

    private void decrementSideFields(Long from, Long delta, String field) {
        String columnName = treeColumnNames.get(field);
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :columnName = (:columnName - ?) where :columnName > ?")
                                .set("columnName", columnName)
                                .build()
                ),
                preparedStatement -> {
                    preparedStatement.setLong(1, delta);
                    preparedStatement.setLong(2, from);
                    setDiscriminatorParams(preparedStatement, 3);
                }
        );
    }
}
