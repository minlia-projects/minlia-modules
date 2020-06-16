
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
    public Integer markNodeIds(NestedSetDetail<ID> nestedSetDetail) {
        return jdbcTemplate.update(
                getDiscriminatedQuery(
                        new Query("update :tableName set :right = (-:right - ?) where :left >= ? and :right <= ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setLong(1, MARKING_MODIFIER);
                    preparedStatement.setLong(2, nestedSetDetail.getLeft());
                    preparedStatement.setLong(3, nestedSetDetail.getRight());
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
    public void updateParentField(ID newParentId, NestedSetDetail<ID> nestedSetDetail) {
        Preconditions.checkNotNull(newParentId);
        doUpdateParentField(newParentId, nestedSetDetail);
    }

    @Override
    public void clearParentField(NestedSetDetail<ID> nestedSetDetail) {
        doUpdateParentField(null, nestedSetDetail);
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
