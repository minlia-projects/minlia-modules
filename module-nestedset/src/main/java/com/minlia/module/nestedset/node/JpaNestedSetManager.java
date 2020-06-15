/**
 * LICENSE
 * <p>
 * This source file is subject to the MIT license that is bundled
 * with this package in the file MIT.txt.
 * It is also available through the world-wide-web at this URL:
 * http://www.opensource.org/licenses/mit-license.html
 */

package com.minlia.module.nestedset.node;

import com.minlia.module.nestedset.annotations.LeftColumn;
import com.minlia.module.nestedset.annotations.LevelColumn;
import com.minlia.module.nestedset.annotations.RightColumn;
import com.minlia.module.nestedset.annotations.RootColumn;
import com.minlia.module.nestedset.config.Configuration;
import net.jcip.annotations.NotThreadSafe;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.*;

/**
 * The default implementation of a JPA {@link NestedSetManager}.
 */
@NotThreadSafe
public class JpaNestedSetManager implements NestedSetManager {

    private final EntityManager entityManager;
    private final Map<Key, Node<?>> nodes;
    private final Map<Class<?>, Configuration> configs;

    public JpaNestedSetManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.nodes = new HashMap<Key, Node<?>>();
        this.configs = new HashMap<Class<?>, Configuration>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public void clear() {
        this.nodes.clear();
    }

    /**
     * {@inheritDoc}
     */
    // @Override
    public Collection<Node<?>> getManagedNodes() {
        return Collections.unmodifiableCollection(this.nodes.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends NodeDetail> List<Node<T>> listNodes(Class<T> clazz) {
        return listNodes(clazz, 0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    
    public <T extends NodeDetail> List<Node<T>> listNodes(Class<T> clazz, Long rootId) {
        Configuration config = getConfig(clazz);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> queryRoot = cq.from(clazz);
        cq.where(cb.ge(queryRoot.<Number>get(config.getLeftFieldName()), 1));
        cq.orderBy(cb.asc(queryRoot.get(config.getLeftFieldName())));
        applyRootId(clazz, cq, rootId);

        List<Node<T>> nodes = new ArrayList<Node<T>>();
        for (T n : entityManager.createQuery(cq).getResultList()) {
            nodes.add(getNode(n));
        }

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends NodeDetail> Node<T> createRoot(T root) {
        if (root.getLeftValue() < root.getRightValue()) {
            throw new IllegalArgumentException("The node already has a position in a tree.");
        }

        Configuration config = getConfig(root.getClass());

        Long maximumRight;
        if (config.hasManyRoots()) {
            maximumRight = 0L;
        } else {
            maximumRight = getMaximumRight(root.getClass());
        }
        root.setLeftValue(maximumRight + 1);
        root.setRightValue(maximumRight + 2);
        root.setLevel(0L);
        entityManager.persist(root);

        return getNode(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    
    public <T extends NodeDetail> Node<T> getNode(T nodeDetail) {
        Key key = new Key(nodeDetail.getClass(), nodeDetail.getId());
        if (this.nodes.containsKey(key)) {
            @SuppressWarnings("unchecked")
            Node<T> n = (Node<T>) this.nodes.get(key);
            return n;
        }
        Node<T> node = new JpaNode<T>(nodeDetail, this);
        if (!node.isValid()) {
            throw new IllegalArgumentException("The given NodeInfo instance has no position " +
                    "in a tree and is thus not yet a node.");
        }
        this.nodes.put(key, node);

        return node;
    }

    Configuration getConfig(Class<?> clazz) {
        if (!this.configs.containsKey(clazz)) {
            Configuration config = new Configuration();

            Entity entity = clazz.getAnnotation(Entity.class);
            String name = entity.name();
            config.setEntityName((name != null && name.length() > 0) ? name : clazz.getSimpleName());

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getAnnotation(LeftColumn.class) != null) {
                    config.setLeftFieldName(field.getName());
                } else if (field.getAnnotation(RightColumn.class) != null) {
                    config.setRightFieldName(field.getName());
                } else if (field.getAnnotation(LevelColumn.class) != null) {
                    config.setLevelFieldName(field.getName());
                } else if (field.getAnnotation(RootColumn.class) != null) {
                    config.setRootIdFieldName(field.getName());
                }
            }

            this.configs.put(clazz, config);
        }

        return this.configs.get(clazz);
    }
    
    private Long getMaximumRight(Class<? extends NodeDetail> clazz) {
        Configuration config = getConfig(clazz);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<? extends NodeDetail> cq = cb.createQuery(clazz);
        Root<? extends NodeDetail> queryRoot = cq.from(clazz);
        cq.orderBy(cb.desc(queryRoot.get(config.getRightFieldName())));
        List<? extends NodeDetail> highestRows = entityManager.createQuery(cq).setMaxResults(1).getResultList();
        if (highestRows.isEmpty()) {
            return 0L;
        } else {
            return highestRows.get(0).getRightValue();
        }
    }
    
    void applyRootId(Class<?> clazz, CriteriaQuery<?> cq, Long rootId) {
        Configuration config = getConfig(clazz);
        if (config.getRootIdFieldName() != null) {
            Root<?> root = cq.getRoots().iterator().next();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            Predicate p = cq.getRestriction();
            cq.where(cb.and(p, cb.equal(root.get(config.getRootIdFieldName()), rootId)));
        }
    }

    
    void updateLeftValues(Long minLeft, Long maxLeft, Long delta, Long rootId) {
        for (Node<?> node : this.nodes.values()) {
            if (node.getRootValue() == rootId) {
                if (node.getLeftValue() >= minLeft && (maxLeft == 0 || node.getLeftValue() <= maxLeft)) {
                    node.setLeftValue(node.getLeftValue() + delta);
                }
            }
        }
    }
    
    void updateRightValues(Long minRight, Long maxRight, Long delta, Long rootId) {
        for (Node<?> node : this.nodes.values()) {
            if (node.getRootValue() == rootId) {
                if (node.getRightValue() >= minRight && (maxRight == 0 || node.getRightValue() <= maxRight)) {
                    node.setRightValue(node.getRightValue() + delta);
                }
            }
        }
    }
    
    void updateLevels(Long left, Long right, Long delta, Long rootId) {
        for (Node<?> node : this.nodes.values()) {
            if (node.getRootValue() == rootId) {
                if (node.getLeftValue() > left && node.getRightValue() < right) {
                    node.setLevel(node.getLevel() + delta);
                }
            }
        }
    }
    
    void removeNodes(Long left, Long right, Long rootId) {
        Set<Key> removed = new HashSet<Key>();
        for (Node<?> node : this.nodes.values()) {
            if (node.getRootValue() == rootId) {
                if (node.getLeftValue() >= left && node.getRightValue() <= right) {
                    removed.add(new Key(node.unwrap().getClass(), node.getId()));
                }
            }
        }
        for (Key k : removed) {
            Node<?> n = this.nodes.remove(k);
            n.setLeftValue(0L);
            n.setRightValue(0L);
            n.setLevel(0L);
            n.setRootValue(0L);
            this.entityManager.detach(n.unwrap());
        }
    }
}
