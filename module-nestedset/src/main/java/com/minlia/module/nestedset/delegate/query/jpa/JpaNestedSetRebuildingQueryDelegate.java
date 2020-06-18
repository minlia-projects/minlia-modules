
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
import com.minlia.module.nestedset.delegate.query.NestedSetRebuildingQueryDelegate;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class JpaNestedSetRebuildingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, ENTITY>
        implements NestedSetRebuildingQueryDelegate<ID, ENTITY> {

    private final static Long UPDATE_INCREMENT_BY = 2L;

    public JpaNestedSetRebuildingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        super(configuration);
    }

    @Override
    public void destroyTree() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);
        update
                .set(root.<Long>get(getLeftFieldName()), 0L)
                .set(root.<Long>get(getRightFieldName()), 0L)
                .set(root.<Long>get(getLevelFieldName()), 0L)
                .where(getPredicates(cb, root));

        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public ENTITY findFirst() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root, cb.isNull(root.get(getParentIdFieldName()))))
                .orderBy(cb.desc(root.get(getIdFieldName())));
        return entityManager.createQuery(select).setMaxResults(1).getSingleResult();
    }

    @Override
    public void resetFirst(ENTITY first) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);
        update
                .set(root.<Long>get(getLevelFieldName()), 0L)
                .set(root.<Long>get(getLeftFieldName()), 1L)
                .set(root.<Long>get(getRightFieldName()), 2L)
                .where(getPredicates(cb, root, cb.equal(update.getRoot().get(getIdFieldName()), first.getId())));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public List<ENTITY> getSiblings(ID first) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.isNull(root.get(getParentIdFieldName())),
                cb.notEqual(root.get(getIdFieldName()), first)
        )).orderBy(cb.asc(root.get(getIdFieldName())));
        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public List<ENTITY> getChildren(ENTITY parent) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root, cb.equal(root.get(getParentIdFieldName()), parent.getId()))).orderBy(cb.asc(root.get(getIdFieldName())));
        return entityManager.createQuery(select).getResultList();
    }
}
