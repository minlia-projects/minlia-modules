package com.minlia.module.nestedset.jpa;

import com.minlia.module.nestedset.base.TestHelper;
import com.minlia.module.nestedset.model.TestSet;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaTestHelper implements TestHelper {

    private final EntityManager em;

    JpaTestHelper(EntityManager em) {
        this.em = em;
    }

    @Override
    public TestSet findNode(String symbol) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TestSet> select = cb.createQuery(TestSet.class);
        Root<TestSet> root = select.from(TestSet.class);
        select.where(cb.equal(root.get("name"), symbol));
        TestSet n = em.createQuery(select).getSingleResult();
        TestHelper.printNode(symbol, n);
        this.em.refresh(n);
        return n;
    }

    @Override
    public TestSet getParent(TestSet f) {
        this.em.refresh(f);
        TestSet parent = null;
        Long parentId = f.getParentId();
        if (parentId != null) {
            parent = em.find(TestSet.class, parentId);
        }
        System.out.println(String.format("Parent of %s is %s", f.getName(), parent != null ? parent.getName() : "null"));
        return parent;
    }

    @Override
    public void breakTree() {
        this.em.createQuery("update TestSet set treeLeft = 0, treeRight = 0, treeLevel = 0 where discriminator = 'tree_1'").executeUpdate();
    }

    @Override
    public void resetParent(String symbol) {
        this.em.createQuery("update TestSet set parentId = null where name='"+symbol+"' and discriminator = 'tree_1'").executeUpdate();
    }

    @Override
    public void removeTree() {
        this.em.createQuery("delete from TestSet where discriminator = 'tree_1'").executeUpdate();
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }

    public void flush() {
        em.flush();
    }

    public void refresh(TestSet node) {
        em.refresh(node);
    }

    public void save(TestSet node) {
        em.persist(node);
    }
}
