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

import com.google.common.base.Preconditions;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetMovingQueryDelegate;

import java.io.Serializable;
import java.sql.Types;

public class JdbcNestedSetMovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JdbcNestedSetQueryDelegate<ID, N>
        implements NestedSetMovingQueryDelegate<ID, N> {

    private final static Long MARKING_MODIFIER = 1000L;

    private enum Mode {
        UP, DOWN
    }

    public JdbcNestedSetMovingQueryDelegate(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }

    @Override
    public Class<?> getClz() {
        return null;
    }

    @Override
    public Integer markNodeIds(NestedSetDetail<ID> node) {
        return jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :right = (-:right - ?) where :left >= ? and :right <= ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setLong(1, MARKING_MODIFIER);
                    preparedStatement.setLong(2, node.getLeft());
                    preparedStatement.setLong(3, node.getRight());
                    setDiscriminatorParams(preparedStatement, 4);
                }
        );
    }

    @Override
    public void updateSideFieldsUp(Long delta, Long start, Long stop, String field) {
        updateFields(Mode.UP, delta, start, stop, field);
    }

    @Override
    public void updateSideFieldsDown(Long delta, Long start, Long stop, String field) {
        updateFields(Mode.DOWN, delta, start, stop, field);
    }

    @Override
    public void performMoveUp(Long nodeDelta, Long levelModificator) {
        performMove(Mode.UP, nodeDelta, levelModificator);
    }

    @Override
    public void performMoveDown(Long nodeDelta, Long levelModificator) {
        performMove(Mode.DOWN, nodeDelta, levelModificator);
    }

    @Override
    public void updateParentField(ID newParentId, NestedSetDetail<ID> node) {
        Preconditions.checkNotNull(newParentId);
        doUpdateParentField(newParentId, node);
    }

    @Override
    public void clearParentField(NestedSetDetail<ID> node) {
        doUpdateParentField(null, node);
    }

    private void updateFields(Mode mode, Long delta, Long start, Long stop, String field) {
        String columnName = treeColumnNames.get(field);
        String sign = Mode.UP.equals(mode) ? "+" : "-";
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :columnName = :columnName :sign ? where :columnName > ? and :columnName < ?")
                                .set("columnName", columnName)
                                .set("sign", sign)
                        .build()
                ),
                preparedStatement -> {
                    preparedStatement.setLong(1, delta);
                    preparedStatement.setLong(2, start);
                    preparedStatement.setLong(3, stop);
                    setDiscriminatorParams(preparedStatement, 4);
                }
        );
    }

    private void performMove(Mode mode, Long nodeDelta, Long levelModificator) {
        String sign = Mode.UP.equals(mode) ? "+" : "-";
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :level = (:level + ?), :right = (-(:right + ?) :sign ?), :left = :left :sign ? where :right < 0")
                                .set("sign", sign)
                                .build()
                ),
                preparedStatement -> {
                    preparedStatement.setLong(1, levelModificator);
                    preparedStatement.setLong(2, MARKING_MODIFIER);
                    preparedStatement.setLong(3, nodeDelta);
                    preparedStatement.setLong(4, nodeDelta);
                    setDiscriminatorParams(preparedStatement, 5);
                }
        );
    }

    private void doUpdateParentField(ID newParentId, NestedSetDetail<ID> node) {
        jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :parentId = ? where :id = ?").build()
                ),
                preparedStatement -> {
                    if(newParentId == null) {
                        preparedStatement.setNull(1, Types.OTHER);
                    } else {
                        preparedStatement.setObject(1, newParentId);
                    }
                    preparedStatement.setObject(2, node.getId());
                    setDiscriminatorParams(preparedStatement, 3);
                }
        );
    }
}
