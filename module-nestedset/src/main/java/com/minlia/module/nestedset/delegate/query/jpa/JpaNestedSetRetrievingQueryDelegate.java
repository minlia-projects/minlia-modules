package com.minlia.module.nestedset.delegate.query.jpa;

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

public class JpaNestedSetRetrievingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, N>
        implements NestedSetRetrievingQueryDelegate<ID, N> {

    private final static Long UPDATE_INCREMENT_BY = 2L;

    public JpaNestedSetRetrievingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }

    @Override
    public List<N> getTreeAsList(N node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), node.getLeft()),
                cb.lessThanOrEqualTo(root.get(getRightFieldName()), node.getRight())
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));

        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public List<N> getChildren(N node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.greaterThanOrEqualTo(root.get(getLeftFieldName()), node.getLeft()),
                cb.lessThanOrEqualTo(root.get(getRightFieldName()), node.getRight()),
                cb.equal(root.<Long>get(getLevelFieldName()), node.getLevel() + 1)
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public Optional<N> getParent(N node) {
        if (node.getLevel() > 0) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<N> select = cb.createQuery(nodeClass);
            Root<N> root = select.from(nodeClass);
            select.where(getPredicates(cb, root,
                    cb.lessThan(root.<Long>get(getLeftFieldName()), node.getLeft()),
                    cb.greaterThan(root.<Long>get(getRightFieldName()), node.getRight()),
                    cb.equal(root.<Long>get(getLevelFieldName()), node.getLevel() - 1)
            )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<N> getParents(N node) {
        if (node.getLevel() > 0) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<N> select = cb.createQuery(nodeClass);
            Root<N> root = select.from(nodeClass);
            select.where(getPredicates(cb, root,
                    cb.lessThan(root.<Long>get(getLeftFieldName()), node.getLeft()),
                    cb.greaterThan(root.<Long>get(getRightFieldName()), node.getRight())
            )).orderBy(cb.desc(root.<Long>get(getLeftFieldName())));
            return entityManager.createQuery(select).getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<N> getPrevSibling(N node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getRightFieldName()), node.getLeft() - 1),
                cb.equal(root.<Long>get(getLevelFieldName()), node.getLevel())
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        try {
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<N> getNextSibling(N node) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
        select.where(getPredicates(cb, root,
                cb.equal(root.<Long>get(getLeftFieldName()), node.getRight() + 1),
                cb.equal(root.<Long>get(getLevelFieldName()), node.getLevel())
        )).orderBy(cb.asc(root.<Long>get(getLeftFieldName())));
        try {
            return Optional.of(entityManager.createQuery(select).setMaxResults(1).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<NestedSetDetail<ID>> getNodeInfo(ID nodeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NestedSetDetail> select = cb.createQuery(NestedSetDetail.class);
        Root<N> root = select.from(nodeClass);
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
    public Optional<N> findFirstRoot() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
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
    public Optional<N> findLastRoot() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<N> select = cb.createQuery(nodeClass);
        Root<N> root = select.from(nodeClass);
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
