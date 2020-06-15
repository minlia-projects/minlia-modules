package com.minlia.module.nestedset.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minlia.module.nestedset.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.NotThreadSafe;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * A decorator for a {@link NodeDetail} implementation that enriches it with the full API
 * of a node in a nested set tree.
 *
 * @param <T extends NodeInfo> The wrapped entity type.
 */
@NotThreadSafe
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = false, allowSetters = false)
class JpaNode<T extends NodeDetail> implements Node<T> {
    private static final Long serialVersionUID = 4L;
    private static final int PREV_SIBLING = 1;
    private static final int FIRST_CHILD = 2;
    private static final int NEXT_SIBLING = 3;
    private static final int LAST_CHILD = 4;

    private final JpaNestedSetManager jpaNestedSetManager;
    private final T node;
    private final Class<T> type;

    private CriteriaQuery<T> baseQuery;
    private Root<T> queryRoot;

    @SuppressWarnings("unchecked")
    public JpaNode(T node, JpaNestedSetManager jpaNestedSetManager) {
        this.node = node;
        this.jpaNestedSetManager = jpaNestedSetManager;
        this.type = (Class<T>) node.getClass();
    }

    @Override
    public Long getId() {
        return this.node.getId();
    }

    @Override
    public Long getLeftValue() {
        return this.node.getLeftValue();
    }

    @Override
    public Long getRightValue() {
        return this.node.getRightValue();
    }

    @Override
    public Long getLevel() {
        return this.node.getLevel();
    }

    @Override
    public Long getRootValue() {
        return this.node.getRootValue();
    }

    @Override
    public void setRootValue(Long value) {
        this.node.setRootValue(value);
    }

    @Override
    public void setLeftValue(Long value) {
        this.node.setLeftValue(value);
    }

    @Override
    public void setRightValue(Long value) {
        this.node.setRightValue(value);
    }

    @Override
    public void setLevel(Long level) {
        this.node.setLevel(level);
    }

    @Override
    public String toString() {
        return "[Left: " + node.getLeftValue() +
                ", Right: " + node.getRightValue() +
                ", Level: " + node.getLevel() +
                ", NodeDetail: " + node.toString() +
                "]";
    }

    @JsonIgnore
    @Override
    public boolean hasChildren() {
        return (getRightValue() - getLeftValue()) > 1;
    }

    @JsonIgnore
    @Override
    public boolean hasParent() {
        return !isRoot();
    }

    @JsonIgnore
    @Override
    public boolean isValid() {
        return isValidNode(this);
    }

    private boolean isValidNode(NodeDetail node) {
        return node != null && node.getRightValue() > node.getLeftValue();
    }


    private CriteriaQuery<T> getBaseQuery() {
        if (this.baseQuery == null) {
            this.baseQuery = jpaNestedSetManager.getEntityManager().getCriteriaBuilder().createQuery(type);
            this.queryRoot = this.baseQuery.from(type);
        }
        return this.baseQuery;
    }

    @JsonIgnore
    public int getNumberOfChildren() {
        return getChildren().size();
    }

    @JsonIgnore
    public Long getNumberOfDescendants() {
        return (this.getRightValue() - this.getLeftValue() - 1) / 2;
    }

    @JsonIgnore
    @Override
    public boolean isRoot() {
        return getLeftValue() == 1;
    }

    @JsonIgnore
    @Override
    public List<Node<T>> getChildren() {
        return getDescendants(1L);
    }

    @JsonIgnore
    @Override
    public Node<T> getParent() {
        if (isRoot()) {
            return null;
        }

        CriteriaBuilder cb = jpaNestedSetManager.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = getBaseQuery();
        cq.where(cb.lt(
                queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getLeftFieldName()),
                getLeftValue()
                ),
                cb.gt(
                        queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getRightFieldName()),
                        getRightValue()
                ));
        cq.orderBy(cb.asc(queryRoot.get(jpaNestedSetManager.getConfig(this.type).getRightFieldName())));
        jpaNestedSetManager.applyRootId(this.type, cq, getRootValue());

        List<T> result = jpaNestedSetManager.getEntityManager().createQuery(cq).getResultList();

        return jpaNestedSetManager.getNode(result.get(0));
    }

    @JsonIgnore
    @Override
    public List<Node<T>> getDescendants() {
        return getDescendants(0L);
    }

    @JsonIgnore
    @Override
    public List<Node<T>> getDescendants(Long depth) {
        CriteriaBuilder cb = jpaNestedSetManager.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = getBaseQuery();
        Predicate wherePredicate = cb.and(
                cb.gt(
                        queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getLeftFieldName()),
                        getLeftValue()
                ),
                cb.lt(
                        queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getRightFieldName()),
                        getRightValue()
                ));

        if (depth > 0) {
            wherePredicate = cb.and(
                    wherePredicate,
                    cb.le(
                            queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getLevelFieldName()),
                            getLevel() + depth)
            );
        }
        cq.where(wherePredicate);
        cq.orderBy(cb.asc(queryRoot.get(jpaNestedSetManager.getConfig(this.type).getLeftFieldName())));

        jpaNestedSetManager.applyRootId(this.type, cq, getRootValue());

        List<Node<T>> nodes = new ArrayList<Node<T>>();
        for (T n : jpaNestedSetManager.getEntityManager().createQuery(cq).getResultList()) {
            nodes.add(jpaNestedSetManager.getNode(n));
        }

        return nodes;
    }

    @JsonIgnore
    @Override
    public Node<T> addChild(T child) {
        if (child == this.node) {
            throw new IllegalArgumentException("Cannot add node as child of itself.");
        }

        Long newLeft = getRightValue();
        Long newRight = getRightValue() + 1;
        Long newRoot = getRootValue();

        shiftRLValues(newLeft, 0L, 2L, newRoot);
        child.setLevel(getLevel() + 1);
        child.setLeftValue(newLeft);
        child.setRightValue(newRight);
        child.setRootValue(newRoot);
        jpaNestedSetManager.getEntityManager().persist(child);

        return this.jpaNestedSetManager.getNode(child);
    }

    private void insertAsPrevSiblingOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot add node as child of itself.");
        }

        Long newLeft = dest.getLeftValue();
        Long newRight = dest.getLeftValue() + 1;
        Long newRoot = dest.getRootValue();

        shiftRLValues(newLeft, 0L, 2L, newRoot);
        setLevel(dest.getLevel());
        setLeftValue(newLeft);
        setRightValue(newRight);
        setRootValue(newRoot);
        jpaNestedSetManager.getEntityManager().persist(this.node);
    }

    private void insertAsNextSiblingOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot add node as child of itself.");
        }

        Long newLeft = dest.getRightValue() + 1;
        Long newRight = dest.getRightValue() + 2;
        Long newRoot = dest.getRootValue();

        shiftRLValues(newLeft, 0L, 2L, newRoot);
        setLevel(dest.getLevel());
        setLeftValue(newLeft);
        setRightValue(newRight);
        setRootValue(newRoot);
        jpaNestedSetManager.getEntityManager().persist(this.node);
    }

    private void insertAsLastChildOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot add node as child of itself.");
        }

        Long newLeft = dest.getRightValue();
        Long newRight = dest.getRightValue() + 1;
        Long newRoot = dest.getRootValue();

        shiftRLValues(newLeft, 0L, 2L, newRoot);
        setLevel(dest.getLevel() + 1);
        setLeftValue(newLeft);
        setRightValue(newRight);
        setRootValue(newRoot);
        jpaNestedSetManager.getEntityManager().persist(this.node);
    }

    private void insertAsFirstChildOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot add node as child of itself.");
        }

        Long newLeft = dest.getLeftValue() + 1L;
        Long newRight = dest.getLeftValue() + 2L;
        Long newRoot = dest.getRootValue();

        shiftRLValues(newLeft, 0L, 2L, newRoot);
        setLevel(dest.getLevel());
        setLeftValue(newLeft);
        setRightValue(newRight);
        setRootValue(newRoot);
        jpaNestedSetManager.getEntityManager().persist(this.node);
    }

    @JsonIgnore
    @Override
    public void delete() {
        Long oldRoot = getRootValue();
        Configuration cfg = jpaNestedSetManager.getConfig(this.type);
        String rootIdFieldName = cfg.getRootIdFieldName();
        String leftFieldName = cfg.getLeftFieldName();
        String rightFieldName = cfg.getRightFieldName();
        String entityName = cfg.getEntityName();

        StringBuilder sb = new StringBuilder();
        sb.append("delete from ")
                .append(entityName).append(" n")
                .append(" where n.").append(leftFieldName).append(">= ?1")
                .append(" and n.").append(rightFieldName).append("<= ?2");

        if (rootIdFieldName != null) {
            sb.append(" and n.").append(rootIdFieldName).append("= ?3");
        }

        Query q = jpaNestedSetManager.getEntityManager().createQuery(sb.toString());
        q.setParameter(1, getLeftValue());
        q.setParameter(2, getRightValue());
        if (rootIdFieldName != null) {
            q.setParameter(3, oldRoot);
        }
        q.executeUpdate();

        // Close gap in tree
        Long first = getRightValue() + 1;
        Long delta = getLeftValue() - getRightValue() - 1;
        shiftRLValues(first, 0L, delta, oldRoot);

        jpaNestedSetManager.removeNodes(getLeftValue(), getRightValue(), oldRoot);
    }

    /**
     * Adds 'delta' to all left and right values that are >= 'first' and
     * <= 'last'. 'delta' can also be negative. If 'last' is 0 it is skipped and there is
     * no upper bound.
     *
     * @param first  The first left/right value (inclusive) of the nodes to shift.
     * @param last   The last left/right value (inclusive) of the nodes to shift.
     * @param delta  The offset by which to shift the left/right values (can be negative).
     * @param rootId The root/tree ID of the nodes to shift.
     */
    protected void shiftRLValues(Long first, Long last, Long delta, Long rootId) {
        Configuration cfg = jpaNestedSetManager.getConfig(this.type);
        String rootIdFieldName = cfg.getRootIdFieldName();
        String leftFieldName = cfg.getLeftFieldName();
        String rightFieldName = cfg.getRightFieldName();
        String entityName = cfg.getEntityName();

        // Shift left values
        StringBuilder sbLeft = new StringBuilder();


        sbLeft.append("update ").append(entityName).append(" n")
                .append(" set n.").append(leftFieldName).append(" = n.").append(leftFieldName).append(" + ?1")
                .append(" where n.").append(leftFieldName).append(" >= ?2");

        //changed from 0 to -1
        Integer current = 2;
        if (last > 0) {
            current++;
            sbLeft.append(" and n.").append(leftFieldName).append(" <= ?" + current);

        }

        if (rootIdFieldName != null) {
            current++;
            sbLeft.append(" and n.").append(rootIdFieldName).append(" = ?" + current);

        }

        log.debug("sbLeft: {}", sbLeft.toString());

        log.debug("Parameter1: {}", delta);
        log.debug("Parameter2: {}", first);
        log.debug("Parameter3: {}", last);
        log.debug("Parameter4: {}", rootId);
        Query qLeft = jpaNestedSetManager.getEntityManager().createQuery(sbLeft.toString());
        qLeft.setParameter(1, delta);
        qLeft.setParameter(2, first);
        //FIXED  changed from 0 to -1
        current = 2;
        if (last > 0) {
            current++;
            qLeft.setParameter(current, last);
        }
        if (rootIdFieldName != null) {
            current++;
            qLeft.setParameter(current, rootId);
        }

        qLeft.executeUpdate();
        this.jpaNestedSetManager.updateLeftValues(first, last, delta, rootId);

        // Shift right values
        StringBuilder sbRight = new StringBuilder();
        sbRight.append("update ").append(entityName).append(" n")
                .append(" set n.").append(rightFieldName).append(" = n.").append(rightFieldName).append(" + ?1")
                .append(" where n.").append(rightFieldName).append(" >= ?2");


        current = 2;
        if (last > 0) {
            current++;
            sbRight.append(" and n.").append(rightFieldName).append(" <= ?").append(current);

        }

        if (rootIdFieldName != null) {
            current++;
            sbRight.append(" and n.").append(rootIdFieldName).append(" = ?").append(current);

        }

        Query qRight = jpaNestedSetManager.getEntityManager().createQuery(sbRight.toString());
        qRight.setParameter(1, delta);
        qRight.setParameter(2, first);

        current = 2;
        if (last > 0) {
            current++;
            qRight.setParameter(current, last);
        }
        if (rootIdFieldName != null) {
            current++;
            qRight.setParameter(current, rootId);
        }
        qRight.executeUpdate();
        this.jpaNestedSetManager.updateRightValues(first, last, delta, rootId);

    }

    @JsonIgnore
    @Override
    public T unwrap() {
        return this.node;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return !hasChildren();
    }

    @JsonIgnore
    @Override
    public Node<T> getFirstChild() {
        CriteriaBuilder cb = jpaNestedSetManager.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = getBaseQuery();
        cq.where(cb.equal(queryRoot.get(jpaNestedSetManager.getConfig(this.type).getLeftFieldName()), getLeftValue() + 1));

        jpaNestedSetManager.applyRootId(this.type, cq, getRootValue());

        return jpaNestedSetManager.getNode(jpaNestedSetManager.getEntityManager().createQuery(cq).getSingleResult());
    }

    @JsonIgnore
    @Override
    public Node<T> getLastChild() {
        CriteriaBuilder cb = jpaNestedSetManager.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = getBaseQuery();
        cq.where(cb.equal(queryRoot.get(jpaNestedSetManager.getConfig(this.type).getRightFieldName()), getRightValue() - 1));

        jpaNestedSetManager.applyRootId(this.type, cq, getRootValue());

        return jpaNestedSetManager.getNode(jpaNestedSetManager.getEntityManager().createQuery(cq).getSingleResult());
    }

    @JsonIgnore
    @Override
    public List<Node<T>> getAncestors() {
        CriteriaBuilder cb = jpaNestedSetManager.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = getBaseQuery();
        Predicate wherePredicate = cb.and(
                cb.lt(queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getLeftFieldName()), getLeftValue()),
                cb.gt(queryRoot.<Number>get(jpaNestedSetManager.getConfig(this.type).getRightFieldName()), getRightValue())
        );

        cq.where(wherePredicate);
        cq.orderBy(cb.asc(queryRoot.get(jpaNestedSetManager.getConfig(this.type).getLeftFieldName())));

        jpaNestedSetManager.applyRootId(this.type, cq, getRootValue());

        List<Node<T>> nodes = new ArrayList<Node<T>>();

        for (T n : jpaNestedSetManager.getEntityManager().createQuery(cq).getResultList()) {
            nodes.add(jpaNestedSetManager.getNode(n));
        }

        return nodes;
    }

    @JsonIgnore
    @Override
    public boolean isDescendantOf(Node<T> subj) {
        return ((getLeftValue() > subj.getLeftValue()) &&
                (getRightValue() < subj.getRightValue()) &&
                (getRootValue() == subj.getRootValue()));
    }

    @JsonIgnore
    public String getPath(String seperator) {
        StringBuilder path = new StringBuilder();
        List<Node<T>> ancestors = getAncestors();
        for (Node<T> ancestor : ancestors) {
            path.append(ancestor.toString()).append(seperator);
        }

        return path.toString();
    }

    @JsonIgnore
    @Override
    public void moveAsPrevSiblingOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot move node as previous sibling of itself");
        }

        if (dest.getRootValue() != getRootValue()) {
            moveBetweenTrees(dest, dest.getLeftValue(), 1);
        } else {
            // Move within the tree
            Long oldLevel = getLevel();
            setLevel(dest.getLevel());
            updateNode(dest.getLeftValue(), getLevel() - oldLevel);
        }
    }

    /**
     * move node's and its children to location 'destLeft' and update rest of tree.
     *
     * @param destLeft  destination left value
     * @param levelDiff
     */
    @JsonIgnore
    private void updateNode(Long destLeft, Long levelDiff) {
        Long left = getLeftValue();
        Long right = getRightValue();
        Long rootId = getRootValue();
        Long treeSize = right - left + 1;

        // Make room in the new branch
        shiftRLValues(destLeft, 0L, treeSize, rootId);

        if (left >= destLeft) { // src was shifted too?
            left += treeSize;
            right += treeSize;
        }

        String levelFieldName = jpaNestedSetManager.getConfig(this.type).getLevelFieldName();
        String leftFieldName = jpaNestedSetManager.getConfig(this.type).getLeftFieldName();
        String rightFieldName = jpaNestedSetManager.getConfig(this.type).getRightFieldName();
        String rootIdFieldName = jpaNestedSetManager.getConfig(this.type).getRootIdFieldName();
        String entityName = jpaNestedSetManager.getConfig(this.type).getEntityName();

        // update level for descendants
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("update ").append(entityName).append(" n")
                .append(" set n.").append(levelFieldName).append(" = n.").append(levelFieldName).append(" + ?1")
                .append(" where n.").append(leftFieldName).append(" > ?2")
                .append(" and n.").append(rightFieldName).append(" < ?3");

        if (rootIdFieldName != null) {
            updateQuery.append(" and n.").append(rootIdFieldName).append(" = ?4");
        }

        Query q = jpaNestedSetManager.getEntityManager().createQuery(updateQuery.toString());
        q.setParameter(1, levelDiff);
        q.setParameter(2, left);
        q.setParameter(3, right);
        if (rootIdFieldName != null) {
            q.setParameter(4, rootId);
        }
        q.executeUpdate();
        this.jpaNestedSetManager.updateLevels(left, right, levelDiff, rootId);

        // now there's enough room next to target to move the subtree
        shiftRLValues(left, right, destLeft - left, rootId);

        // correct values after source (close gap in old tree)
        shiftRLValues(right + 1, 0L, -treeSize, rootId);
    }

    @JsonIgnore
    @Override
    public void moveAsNextSiblingOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot move node as next sibling of itself");
        }
        if (dest.getRootValue() != getRootValue()) {
            moveBetweenTrees(dest, dest.getRightValue() + 1, 3);
        } else {
            // Move within tree
            Long oldLevel = getLevel();
            setLevel(dest.getLevel());
            updateNode(dest.getRightValue() + 1, getLevel() - oldLevel);
        }
    }

    @JsonIgnore
    @Override
    public void moveAsFirstChildOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot move node as first child of itself");
        }

        if (dest.getRootValue() != getRootValue()) {
            moveBetweenTrees(dest, dest.getLeftValue() + 1, 2);
        } else {
            // Move within tree
            Long oldLevel = getLevel();
            setLevel(dest.getLevel() + 1);
            updateNode(dest.getLeftValue() + 1, getLevel() - oldLevel);
        }
    }

    @JsonIgnore
    @Override
    public void moveAsLastChildOf(Node<T> dest) {
        if (dest == this.node) {
            throw new IllegalArgumentException("Cannot move node as first child of itself");
        }

        if (dest.getRootValue() != getRootValue()) {
            moveBetweenTrees(dest, dest.getLeftValue() + 1, 4);
        } else {
            // Move within tree
            Long oldLevel = getLevel();
            setLevel(dest.getLevel() + 1);
            updateNode(dest.getRightValue(), getLevel() - oldLevel);
        }
    }

    /**
     * Accomplishes moving of nodes between different trees.
     * Used by the move* methods if the root values of the two nodes are different.
     *
     * @param dest
     * @param newLeftValue
     * @param moveType
     */
    @JsonIgnore
    private void moveBetweenTrees(Node<T> dest, Long newLeftValue, Integer moveType) {

        Configuration cfg = jpaNestedSetManager.getConfig(this.type);
        String leftFieldName = cfg.getLeftFieldName();
        String rightFieldName = cfg.getRightFieldName();
        String levelFieldName = cfg.getLevelFieldName();
        String rootIdFieldName = cfg.getRootIdFieldName();
        String entityName = cfg.getEntityName();

        // Move between trees: Detach from old tree & insert into new tree
        Long newRoot = dest.getRootValue();
        Long oldRoot = getRootValue();
        Long oldLft = getLeftValue();
        Long oldRgt = getRightValue();
        Long oldLevel = getLevel();

        // Prepare target tree for insertion, make room
        shiftRLValues(newLeftValue, 0L, oldRgt - oldLft - 1, newRoot);

        // Set new root id for this node
        setRootValue(newRoot);
        //$this ->  _node ->  save();
        // Insert this node as a new node
        setRightValue(0L);
        setLeftValue(0L);

        switch (moveType) {
            case PREV_SIBLING:
                insertAsPrevSiblingOf(dest);
                break;
            case FIRST_CHILD:
                insertAsFirstChildOf(dest);
                break;
            case NEXT_SIBLING:
                insertAsNextSiblingOf(dest);
                break;
            case LAST_CHILD:
                insertAsLastChildOf(dest);
                break;
            default:
                throw new IllegalArgumentException("Unknown move operation: " + moveType);
        }

        setRightValue(getLeftValue() + (oldRgt - oldLft));

        Long newLevel = getLevel();
        Long levelDiff = newLevel - oldLevel;

        // Relocate descendants of the node
        Long diff = getLeftValue() - oldLft;

        // Update lft/rgt/root/level for all descendants
        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("update ").append(entityName).append(" n")
                .append(" set n.").append(leftFieldName).append(" = n.").append(leftFieldName).append(" + ?1")
                .append(", n.").append(rightFieldName).append(" = n.").append(rightFieldName).append(" + ?2")
                .append(", n.").append(levelFieldName).append(" = n.").append(levelFieldName).append(" + ?3")
                .append(", n.").append(rootIdFieldName).append(" = ?4")
                .append(" where n.").append(leftFieldName).append(" > ?5")
                .append(" and n.").append(rightFieldName).append(" < ?6")
                .append(" and n.").append(rootIdFieldName).append(" = ?7");

        Query q = jpaNestedSetManager.getEntityManager().createQuery(updateQuery.toString());
        q.setParameter(1, diff);
        q.setParameter(2, diff);
        q.setParameter(3, levelDiff);
        q.setParameter(4, newRoot);
        q.setParameter(5, oldLft);
        q.setParameter(6, oldRgt);
        q.setParameter(7, oldRoot);

        q.executeUpdate();

        // Close gap in old tree
        Long first = oldRgt + 1;
        Long delta = oldLft - oldRgt - 1;
        shiftRLValues(first, 0L, delta, oldRoot);
    }

    @JsonIgnore
    public void makeRoot(Long newRootId) {
        if (isRoot()) {
            return;
        }

        Configuration cfg = jpaNestedSetManager.getConfig(this.type);
        String leftFieldName = cfg.getLeftFieldName();
        String rightFieldName = cfg.getRightFieldName();
        String levelFieldName = cfg.getLevelFieldName();
        String rootIdFieldName = cfg.getRootIdFieldName();
        String entityName = cfg.getEntityName();

        Long oldRgt = getRightValue();
        Long oldLft = getLeftValue();
        Long oldRoot = getRootValue();
        Long oldLevel = getLevel();

        // Update descendants lft/rgt/root/level values
        Long diff = 1L - oldLft;
        Long newRoot = newRootId;

        StringBuilder updateQuery = new StringBuilder();
        updateQuery.append("update ").append(entityName).append(" n")
                .append(" set n.").append(leftFieldName).append(" = n.").append(leftFieldName).append(" + ?1")
                .append(", n.").append(rightFieldName).append(" = n.").append(rightFieldName).append(" + ?2")
                .append(", n.").append(levelFieldName).append(" = n.").append(levelFieldName).append(" - ?3")
                .append(", n.").append(rootIdFieldName).append(" = ?4")
                .append("where n.").append(leftFieldName).append(" > ?5")
                .append(" and n.").append(rightFieldName).append(" < ?6")
                .append(" and n.").append(rootIdFieldName).append(" = ?7");

        Query q = jpaNestedSetManager.getEntityManager().createQuery(updateQuery.toString());
        q.setParameter(1, diff);
        q.setParameter(2, diff);
        q.setParameter(3, oldLevel);
        q.setParameter(4, newRoot);
        q.setParameter(5, oldLft);
        q.setParameter(6, oldRgt);
        q.setParameter(7, oldRoot);

        q.executeUpdate();

        // Detach from old tree (close gap in old tree)
        Long first = oldRgt + 1;
        Long delta = oldLft - oldRgt - 1;
        shiftRLValues(first, 0L, delta, getRootValue());

        // Set new lft/rgt/root/level values for root node
        setLeftValue(1L);
        setRightValue(oldRgt - oldLft + 1);
        setRootValue(newRootId);
        setLevel(0L);
    }
}
