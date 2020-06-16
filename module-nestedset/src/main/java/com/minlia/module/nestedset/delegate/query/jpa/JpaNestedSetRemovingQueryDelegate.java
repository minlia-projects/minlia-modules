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

import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetRemovingQueryDelegate;
import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.Optional;

public class JpaNestedSetRemovingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, ENTITY>
        implements NestedSetRemovingQueryDelegate<ID, ENTITY> {

    private final static Long UPDATE_INCREMENT_BY = 2L;

    public JpaNestedSetRemovingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        super(configuration);
    }

    @Override
    public void setNewParentForDeletedNodesChildren(NestedSetDetail<ID> nestedSetDetail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);
        update.set(root.get(getParentIdFieldName()),  findNodeParentId(nestedSetDetail).orElse(null))
                .where(getPredicates(cb, root,
                        cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), nestedSetDetail.getLeft()),
                        cb.lessThanOrEqualTo(root.get(getRightFieldName()), nestedSetDetail.getRight()),
                        cb.equal(root.<Long>get(getLevelFieldName()), nestedSetDetail.getLevel() + 1)
                ));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void performSingleDeletion(NestedSetDetail<ID> nestedSetDetail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<ENTITY> delete = cb.createCriteriaDelete(nodeClass);
        Root<ENTITY> root = delete.from(nodeClass);
        delete.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getIdFieldName()), nestedSetDetail.getId())
        ));
        entityManager.createQuery(delete).executeUpdate();
    }

    private Optional<ID> findNodeParentId(NestedSetDetail<ID> nestedSetDetail) {
        if (nestedSetDetail.getLevel() > 0) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ID> select = cb.createQuery(idClass);
            Root<ENTITY> root = select.from(nodeClass);
            select.select(root.get(getIdFieldName())).where(getPredicates(cb, root,
                    cb.lessThan(root.get(getLeftFieldName()), nestedSetDetail.getLeft()),
                    cb.greaterThan(root.get(getRightFieldName()), nestedSetDetail.getRight()),
                    cb.equal(root.<Long>get(getLevelFieldName()), nestedSetDetail.getLevel() - 1)
            ));
            try {
                return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
            } catch (NoResultException ex) {
                throw new InvalidNodeException(String.format("Couldn't find node's parent, although its level is greater than 0. It seems the tree is malformed: %s", nestedSetDetail));
            }
        }
        return Optional.empty();
    }

    @Override
    public void decrementSideFieldsBeforeSingleNodeRemoval(Long from, String field) {
        decrementSideFields(from, DECREMENT_BY, field);
    }

    @Override
    public void pushUpDeletedNodesChildren(NestedSetDetail<ID> nestedSetDetail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);
        update.set(root.<Long>get(getRightFieldName()), cb.diff(root.get(getRightFieldName()), 1L))
                .set(root.<Long>get(getLeftFieldName()), cb.diff(root.get(getLeftFieldName()), 1L))
                .set(root.<Long>get(getLevelFieldName()), cb.diff(root.get(getLevelFieldName()), 1L));

        update.where(getPredicates(cb, root,
                cb.lessThan(root.get(getRightFieldName()), nestedSetDetail.getRight()),
                cb.greaterThan(root.get(getLeftFieldName()), nestedSetDetail.getLeft()))
        );

        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void decrementSideFieldsAfterSubtreeRemoval(Long from, Long delta, String field) {
        decrementSideFields(from, delta, field);
    }

    @Override
    public void performBatchDeletion(NestedSetDetail<ID> nestedSetDetail) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<ENTITY> delete = cb.createCriteriaDelete(nodeClass);
        Root<ENTITY> root = delete.from(nodeClass);
        delete.where(getPredicates(cb, root,
                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), nestedSetDetail.getLeft()),
                cb.lessThanOrEqualTo(root.get(getRightFieldName()), nestedSetDetail.getRight())
        ));

        entityManager.createQuery(delete).executeUpdate();
    }

    private void decrementSideFields(Long from, Long delta, String field) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);

        update.set(root.<Long>get(field), cb.diff(root.get(field), delta))
                .where(getPredicates(cb, root, cb.greaterThan(root.get(field), from)));

        entityManager.createQuery(update).executeUpdate();
    }
}
