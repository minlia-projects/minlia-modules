/*
 *  The MIT License
 *
 *  Copyright (c) 2019 eXsio.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 *  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 *  the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 *  BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.minlia.module.nestedset.delegate.query.jdbc;

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
    public void setNewParentForDeletedNodesChildren(NestedSetDetail<ID> node) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :parentId = ? where :left >= ? and :right <= ? and :level = ?").build()
                ),
                preparedStatement -> {
                    Optional<ID> newParentId = findNodeParentId(node);
                    if (!newParentId.isPresent()) {
                        preparedStatement.setNull(1, Types.OTHER);
                    } else {
                        preparedStatement.setObject(1, newParentId.get());
                    }
                    preparedStatement.setLong(2, node.getLeft());
                    preparedStatement.setLong(3, node.getRight());
                    preparedStatement.setLong(4, node.getLevel() + 1);
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
    public void performSingleDeletion(NestedSetDetail<ID> node) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("delete from :tableName where :id = ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getId());
                    setDiscriminatorParams(preparedStatement, 2);
                }
        );
    }

    @Override
    public void decrementSideFieldsBeforeSingleNodeRemoval(Long from, String field) {
        decrementSideFields(from, DECREMENT_BY, field);
    }

    @Override
    public void pushUpDeletedNodesChildren(NestedSetDetail<ID> node) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :right = (:right - 1), :left = (:left - 1), :level = (:level - 1) where :right < ? and :left > ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getRight());
                    preparedStatement.setObject(2, node.getLeft());
                    setDiscriminatorParams(preparedStatement, 3);
                }
        );
    }

    @Override
    public void decrementSideFieldsAfterSubtreeRemoval(Long from, Long delta, String field) {
        decrementSideFields(from, delta, field);
    }

    @Override
    public void performBatchDeletion(NestedSetDetail<ID> node) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("delete from :tableName where :left >= ? and :right <= ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getLeft());
                    preparedStatement.setObject(2, node.getRight());
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
