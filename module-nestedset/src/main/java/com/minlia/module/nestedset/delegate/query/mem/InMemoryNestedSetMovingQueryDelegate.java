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

package com.minlia.module.nestedset.delegate.query.mem;

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
    public Integer markNodeIds(NestedSetDetail<ID> node) {
        return Math.toIntExact(nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) >= node.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) <= node.getRight())
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
    public void updateParentField(ID newParentId, NestedSetDetail<ID> node) {
        Preconditions.checkNotNull(newParentId);
        doUpdateParentField(newParentId, node);
    }

    @Override
    public void clearParentField(NestedSetDetail<ID> node) {
        doUpdateParentField(null, node);
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
