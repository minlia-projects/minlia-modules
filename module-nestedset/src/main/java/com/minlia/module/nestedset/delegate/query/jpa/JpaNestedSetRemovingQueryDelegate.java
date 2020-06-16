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
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYgetRightFieldName() HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.minlia.module.nestedset.delegate.query.jpa;

import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetRemovingQueryDelegate;
import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.Optional;

public class JpaNestedSetRemovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, N>
        implements NestedSetRemovingQueryDelegate<ID, N> {

    private final static Long UPDATE_INCREMENT_BY = 2L;

    public JpaNestedSetRemovingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }

    @Override
    public void setNewParentForDeletedNodesChildren(NestedSetDetail<ID> node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);
        update.set(root.get(getParentIdFieldName()),  findNodeParentId(node).orElse(null))
                .where(getPredicates(cb, root,
                        cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), node.getLeft()),
                        cb.lessThanOrEqualTo(root.get(getRightFieldName()), node.getRight()),
                        cb.equal(root.<Long>get(getLevelFieldName()), node.getLevel() + 1)
                ));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void performSingleDeletion(NestedSetDetail<ID> node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<N> delete = cb.createCriteriaDelete(nodeClass);
        Root<N> root = delete.from(nodeClass);
        delete.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getIdFieldName()), node.getId())
        ));
        entityManager.createQuery(delete).executeUpdate();
    }

    private Optional<ID> findNodeParentId(NestedSetDetail<ID> node) {
        if (node.getLevel() > 0) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ID> select = cb.createQuery(idClass);
            Root<N> root = select.from(nodeClass);
            select.select(root.get(getIdFieldName())).where(getPredicates(cb, root,
                    cb.lessThan(root.get(getLeftFieldName()), node.getLeft()),
                    cb.greaterThan(root.get(getRightFieldName()), node.getRight()),
                    cb.equal(root.<Long>get(getLevelFieldName()), node.getLevel() - 1)
            ));
            try {
                return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
            } catch (NoResultException ex) {
                throw new InvalidNodeException(String.format("Couldn't find node's parent, although its level is greater than 0. It seems the tree is malformed: %s", node));
            }
        }
        return Optional.empty();
    }

    @Override
    public void decrementSideFieldsBeforeSingleNodeRemoval(Long from, String field) {
        decrementSideFields(from, DECREMENT_BY, field);
    }

    @Override
    public void pushUpDeletedNodesChildren(NestedSetDetail<ID> node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);
        update.set(root.<Long>get(getRightFieldName()), cb.diff(root.get(getRightFieldName()), 1L))
                .set(root.<Long>get(getLeftFieldName()), cb.diff(root.get(getLeftFieldName()), 1L))
                .set(root.<Long>get(getLevelFieldName()), cb.diff(root.get(getLevelFieldName()), 1L));

        update.where(getPredicates(cb, root,
                cb.lessThan(root.get(getRightFieldName()), node.getRight()),
                cb.greaterThan(root.get(getLeftFieldName()), node.getLeft()))
        );

        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void decrementSideFieldsAfterSubtreeRemoval(Long from, Long delta, String field) {
        decrementSideFields(from, delta, field);
    }

    @Override
    public void performBatchDeletion(NestedSetDetail<ID> node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<N> delete = cb.createCriteriaDelete(nodeClass);
        Root<N> root = delete.from(nodeClass);
        delete.where(getPredicates(cb, root,
                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), node.getLeft()),
                cb.lessThanOrEqualTo(root.get(getRightFieldName()), node.getRight())
        ));

        entityManager.createQuery(delete).executeUpdate();
    }

    private void decrementSideFields(Long from, Long delta, String field) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);

        update.set(root.<Long>get(field), cb.diff(root.get(field), delta))
                .where(getPredicates(cb, root, cb.greaterThan(root.get(field), from)));

        entityManager.createQuery(update).executeUpdate();
    }
}
