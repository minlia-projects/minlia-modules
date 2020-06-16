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

package com.minlia.module.nestedset.delegate.query.jpa;

import com.google.common.base.Preconditions;
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetMovingQueryDelegate;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public class JpaNestedSetMovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, N>
        implements NestedSetMovingQueryDelegate<ID, N> {

    private enum Mode {
        UP, DOWN
    }

    private final static Long MARKING_MODIFIER = 1000L;

    public JpaNestedSetMovingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public Class<?> getClz() {
        return nodeClass;
    }

    @Override
    public Integer markNodeIds(NestedSetDetail<ID> node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);
        update
//                .set(root.<Long>get(RIGHT), markRightField(root))
                .set(root.<Long>get(getRightFieldName()), markRightField(root))
                .where(
                        getPredicates(cb, root,
//                                cb.greaterThanOrEqualTo(root.get(LEFT), node.getLeft()),
//                                cb.lessThanOrEqualTo(root.get(RIGHT), node.getRight())
                                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), node.getLeft()),
                                cb.lessThanOrEqualTo(root.get(getRightFieldName()), node.getRight())
                        ));
        return entityManager.createQuery(update).executeUpdate();
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);

        if (Mode.DOWN.equals(mode)) {
            update.set(root.<Long>get(field), cb.diff(root.get(field), delta));
        } else if (Mode.UP.equals(mode)) {
            update.set(root.<Long>get(field), cb.sum(root.get(field), delta));
        }
        update.where(getPredicates(cb, root,
                cb.greaterThan(root.get(field), start),
                cb.lessThan(root.get(field), stop)
        ));
        entityManager.createQuery(update).executeUpdate();
    }

    private void performMove(Mode mode, Long nodeDelta, Long levelModificator) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);

        update.set(root.<Long>get(getLevelFieldName()), cb.sum(root.get(getLevelFieldName()), levelModificator));
        if (Mode.DOWN.equals(mode)) {
            update.set(root.<Long>get(getRightFieldName()), cb.diff(unMarkRightField(root), nodeDelta));
            update.set(root.<Long>get(getLeftFieldName()), cb.diff(root.get(getLeftFieldName()), nodeDelta));
        } else if (Mode.UP.equals(mode)) {
            update.set(root.<Long>get(getRightFieldName()), cb.sum(unMarkRightField(root), nodeDelta));
            update.set(root.<Long>get(getLeftFieldName()), cb.sum(root.get(getLeftFieldName()), nodeDelta));
        }
        update.where(
                getPredicates(cb, root, cb.lessThan(root.get(getRightFieldName()), 0))
        );
        entityManager.createQuery(update).executeUpdate();
    }

    private Expression<Long> markRightField(Root<N> root) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        return cb.diff(cb.neg(root.get(getRightFieldName())), MARKING_MODIFIER);
    }

    private Expression<Long> unMarkRightField(Root<N> root) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        return cb.neg(cb.sum(root.get(getRightFieldName()), MARKING_MODIFIER));
    }

    private void doUpdateParentField(ID newParentId, NestedSetDetail<ID> node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);

        update.set(root.get(getParentIdFieldName()), newParentId)
                .where(getPredicates(cb, root, cb.equal(root.get(getIdFieldName()), node.getId())));

        entityManager.createQuery(update).executeUpdate();
    }
}
