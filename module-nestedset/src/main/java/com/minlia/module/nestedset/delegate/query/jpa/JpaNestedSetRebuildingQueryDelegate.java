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
import com.minlia.module.nestedset.delegate.query.NestedSetRebuildingQueryDelegate;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class JpaNestedSetRebuildingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, N>
        implements NestedSetRebuildingQueryDelegate<ID, N> {

    private final static Long UPDATE_INCREMENT_BY = 2L;

    public JpaNestedSetRebuildingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }

    @Override
    public void destroyTree() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);
        update
                .set(root.<Long>get(getLeftFieldName()), 0L)
                .set(root.<Long>get(getRightFieldName()), 0L)
                .set(root.<Long>get(getLevelFieldName()), 0L)
                .where(getPredicates(cb, root));

        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public N findFirst() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root, cb.isNull(root.get(getParentIdFieldName()))))
                .orderBy(cb.desc(root.get(getIdFieldName())));
        return entityManager.createQuery(select).setMaxResults(1).getSingleResult();
    }

    @Override
    public void resetFirst(N first) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<N> update = cb.createCriteriaUpdate(nodeClass);
        Root<N> root = update.from(nodeClass);
        update
                .set(root.<Long>get(getLevelFieldName()), 0L)
                .set(root.<Long>get(getLeftFieldName()), 1L)
                .set(root.<Long>get(getRightFieldName()), 2L)
                .where(getPredicates(cb, root, cb.equal(update.getRoot().get(getIdFieldName()), first.getId())));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public List<N> getSiblings(ID first) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.isNull(root.get(getParentIdFieldName())),
                cb.notEqual(root.get(getIdFieldName()), first)
        )).orderBy(cb.asc(root.get(getIdFieldName())));
        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public List<N> getChildren(N parent) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root, cb.equal(root.get(getParentIdFieldName()), parent.getId()))).orderBy(cb.asc(root.get(getIdFieldName())));
        return entityManager.createQuery(select).getResultList();
    }
}
