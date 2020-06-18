
package com.minlia.module.nestedset.delegate.query.jpa;

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

public class JpaNestedSetMovingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, ENTITY>
        implements NestedSetMovingQueryDelegate<ID, ENTITY> {

    private enum Mode {
        UP, DOWN
    }

    private final static Long MARKING_MODIFIER = 1000L;

    public JpaNestedSetMovingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        super(configuration);
    }


    @Override
    public Class<?> getClz() {
        return nodeClass;
    }

    @Override
    public Integer markNodeIds(NestedSetDetail<ID> nestedSetDetail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);
        update
//                .set(root.<Long>get(RIGHT), markRightField(root))
                .set(root.<Long>get(getRightFieldName()), markRightField(root))
                .where(
                        getPredicates(cb, root,
//                                cb.greaterThanOrEqualTo(root.get(LEFT), node.getLeft()),
//                                cb.lessThanOrEqualTo(root.get(RIGHT), node.getRight())
                                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), nestedSetDetail.getLeft()),
                                cb.lessThanOrEqualTo(root.get(getRightFieldName()), nestedSetDetail.getRight())
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
    public void updateParentField(ID newParentId, NestedSetDetail<ID> nestedSetDetail) {
        Preconditions.checkNotNull(newParentId);
        doUpdateParentField(newParentId, nestedSetDetail);
    }

    @Override
    public void clearParentField(NestedSetDetail<ID> nestedSetDetail) {
        doUpdateParentField(null, nestedSetDetail);
    }

    private void updateFields(Mode mode, Long delta, Long start, Long stop, String field) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);

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
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);

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

    private Expression<Long> markRightField(Root<ENTITY> root) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        return cb.diff(cb.neg(root.get(getRightFieldName())), MARKING_MODIFIER);
    }

    private Expression<Long> unMarkRightField(Root<ENTITY> root) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        return cb.neg(cb.sum(root.get(getRightFieldName()), MARKING_MODIFIER));
    }

    private void doUpdateParentField(ID newParentId, NestedSetDetail<ID> nestedSetDetail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);

        update.set(root.get(getParentIdFieldName()), newParentId)
                .where(getPredicates(cb, root, cb.equal(root.get(getIdFieldName()), nestedSetDetail.getId())));

        entityManager.createQuery(update).executeUpdate();
    }
}
