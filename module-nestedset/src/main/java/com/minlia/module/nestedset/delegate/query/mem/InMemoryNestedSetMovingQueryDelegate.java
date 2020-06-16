
package com.minlia.module.nestedset.delegate.query.mem;

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
import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.delegate.query.NestedSetMovingQueryDelegate;

import java.io.Serializable;

public class InMemoryNestedSetMovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends InMemoryNestedNodeQueryDelegate<ID, N>
        implements NestedSetMovingQueryDelegate<ID, N> {

    private final static Long MARKING_MODIFIER = 1000L;

    private enum Mode {
        UP, DOWN
    }

    public InMemoryNestedSetMovingQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public Class<?> getClz() {
        return null;
    }

    @Override
    public Integer markNodeIds(NestedSetDetail<ID> nestedSetDetail) {
        return Math.toIntExact(nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) >= nestedSetDetail.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) <= nestedSetDetail.getRight())
                .peek(n -> setLong(NestedSet.RIGHT, n, negate(getLong(NestedSet.RIGHT, n)) - MARKING_MODIFIER))
                .count());
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
        nodesStream()
                .filter(n -> getLong(field, n) > start)
                .filter(n -> getLong(field, n) < stop)
                .forEach(n -> {
                    if (Mode.DOWN.equals(mode)) {
                        setLong(field, n, getLong(field, n) - delta);
                    } else if (Mode.UP.equals(mode)) {
                        setLong(field, n, getLong(field, n) + delta);
                    }
                });
    }

    private void performMove(Mode mode, Long nodeDelta, Long levelModificator) {
        nodesStream()
                .filter(n -> getLong(NestedSet.RIGHT, n) < 0)
                .forEach(n -> {
                    setLong(NestedSet.LEVEL, n, getLong(NestedSet.LEVEL, n) + levelModificator);
                    Long right = negate(getLong(NestedSet.RIGHT, n) + MARKING_MODIFIER);
                    if (Mode.DOWN.equals(mode)) {
                        setLong(NestedSet.RIGHT, n, right - nodeDelta);
                        setLong(NestedSet.LEFT, n, getLong(NestedSet.LEFT, n) - nodeDelta);
                    } else if (Mode.UP.equals(mode)) {
                        setLong(NestedSet.RIGHT, n, right + nodeDelta);
                        setLong(NestedSet.LEFT, n, getLong(NestedSet.LEFT, n) + nodeDelta);
                    }
                });
    }

    private void doUpdateParentField(ID newParentId, NestedSetDetail<ID> node) {
        nodesStream()
                .filter(n -> getSerializable(NestedSet.ID, n).equals(node.getId()))
                .forEach(n -> n.setParentId(newParentId));
    }

    private Long negate(Long value) {
        return value * -1;
    }
}
