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
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaNestedSetRetrievingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, ENTITY>
        implements NestedSetRetrievingQueryDelegate<ID, ENTITY> {

    private final static Long UPDATE_INCREMENT_BY = 2L;

    public JpaNestedSetRetrievingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        super(configuration);
    }

    @Override
    public List<ENTITY> getTreeAsList(ENTITY entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), entity.getLeft()),
                cb.lessThanOrEqualTo(root.get(getRightFieldName()), entity.getRight())
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));

        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public List<ENTITY> getChildren(ENTITY entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), entity.getLeft()),
                cb.lessThanOrEqualTo(root.get(getRightFieldName()), entity.getRight()),
                cb.equal(root.<Long>get(getLevelFieldName()), entity.getLevel() + 1)
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public Optional<ENTITY> getParent(ENTITY entity) {
        if (entity.getLevel() > 0) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
            Root<ENTITY> root = select.from(nodeClass);
            select.where(getPredicates(cb, root,
                    cb.lessThan(root.<Long>get(getLeftFieldName()), entity.getLeft()),
                    cb.greaterThan(root.<Long>get(getRightFieldName()), entity.getRight()),
                    cb.equal(root.<Long>get(getLevelFieldName()), entity.getLevel() - 1)
            )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<ENTITY> getParents(ENTITY entity) {
        if (entity.getLevel() > 0) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
            Root<ENTITY> root = select.from(nodeClass);
            select.where(getPredicates(cb, root,
                    cb.lessThan(root.<Long>get(getLeftFieldName()), entity.getLeft()),
                    cb.greaterThan(root.<Long>get(getRightFieldName()), entity.getRight())
            )).orderBy(cb.desc(root.<Long>get(getLeftFieldName())));
            return entityManager.createQuery(select).getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<ENTITY> getPrevSibling(ENTITY entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getRightFieldName()), entity.getLeft() - 1),
                cb.equal(root.<Long>get(getLevelFieldName()), entity.getLevel())
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        try {
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ENTITY> getNextSibling(ENTITY entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getLeftFieldName()), entity.getRight() + 1),
                cb.equal(root.<Long>get(getLevelFieldName()), entity.getLevel())
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        try {
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<NestedSetDetail<ID>> getNestedSetDetail(ID nodeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NestedSetDetail> select = cb.createQuery(NestedSetDetail.class);
        Root<ENTITY> root = select.from(nodeClass);
        select.select(
                cb.construct(
                        NestedSetDetail.class,
                        root.get(getIdFieldName()),
                        root.get(getParentIdFieldName()),
                        root.get(getLeftFieldName()),
                        root.get(getRightFieldName()),
                        root.get(getLevelFieldName())
                )
        ).where(cb.equal(root.get(getIdFieldName()), nodeId));
        try {
            return Optional.of(entityManager.createQuery(select).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ENTITY> findFirstRoot() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getLevelFieldName()), 0L)
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        try {
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ENTITY> findLastRoot() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ENTITY> select = cb.createQuery(nodeClass);
        Root<ENTITY> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getLevelFieldName()), 0L)
        )).orderBy(cb.desc(root.<Long>get(getLeftFieldName())));
        try {
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
