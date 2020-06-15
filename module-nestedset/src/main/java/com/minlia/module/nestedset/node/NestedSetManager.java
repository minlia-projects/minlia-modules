package com.minlia.module.nestedset.node;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * A <tt>NestedSetManager</tt> is used to read and manipulate the nested set
 * tree structure intrinsic to classes that implement {@link NodeDetail}.
 */
public interface NestedSetManager {
    /**
     * Clears the NestedSetManager, removing all managed nodes from the
     * <tt>NestedSetManager</tt>. Any entities wrapped by such nodes are
     * not detached from the underlying {@link EntityManager}.
     */
    // void clear();

    /**
     * Create a root node for the given NodeInfo instance.
     *
     * @param <T>
     * @param root
     * @return The created node instance.
     */
    <T extends NodeDetail> Node<T> createRoot(T root);

    /**
     * List all nodes of a tree, in ascending order of {@link NodeDetail#getLeftValue}.
     *
     * @param <T>
     * @param clazz
     * @return The nodes of the tree.
     */
    <T extends NodeDetail> List<Node<T>> listNodes(Class<T> clazz);

    /**
     * List all nodes of a tree, in ascending order of {@link NodeDetail#getLeftValue}.
     *
     * @param <T>
     * @param clazz
     * @param rootId The tree ID.
     * @return The nodes of the tree.
     */
    <T extends NodeDetail> List<Node<T>> listNodes(Class<T> clazz, Long rootId);

    /**
     * Get the EntityManager used by this NestedSetManager.
     *
     * @return The EntityManager.
     */
    EntityManager getEntityManager();

    /**
     * Get the node that represents the given NodeInfo instance in the tree.
     *
     * @param <T>
     * @param nodeInfo
     * @return The node.
     */
    <T extends NodeDetail> Node<T> getNode(T nodeInfo);

    /**
     * Gets a collection of all nodes currently managed by the NestedSetManager.
     *
     * @return The collection of managed nodes.
     */
    // Collection<Node<?>> getManagedNodes();
}
