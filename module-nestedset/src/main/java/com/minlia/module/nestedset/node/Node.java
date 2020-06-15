package com.minlia.module.nestedset.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * A node in a nested set tree.
 *
 * @param <T extends NodeDetail> The wrapped entity type.
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = false, allowSetters = false)
public interface Node<T extends NodeDetail> extends NodeDetail {
    /**
     * move as last child of destination
     *
     * @param destination
     */
    @JsonIgnore
    void moveAsLastChildOf(Node<T> destination);

    /**
     * move as first child of destination
     *
     * @param destination
     */
    @JsonIgnore
    void moveAsFirstChildOf(Node<T> destination);

    /**
     * move as next sibling of destination
     *
     * @param destination
     */
    @JsonIgnore
    void moveAsNextSiblingOf(Node<T> destination);


    /**
     * move as previous sibling of destination
     *
     * @param destination
     */
    @JsonIgnore
    void moveAsPrevSiblingOf(Node<T> destination);

    /**
     * get all children nodes
     *
     * @return
     */
    @JsonIgnore
    List<Node<T>> getChildren();

    /**
     * get all descendant nodes by depth defined
     *
     * @param depth
     * @return
     */
    @JsonIgnore
    List<Node<T>> getDescendants(Long depth);

    /**
     * get all descendant nodes
     *
     * @return
     */
    @JsonIgnore
    List<Node<T>> getDescendants();

    /**
     * get all ancestor nodes
     *
     * @return
     */
    @JsonIgnore
    List<Node<T>> getAncestors();

    /**
     * add a child to node
     *
     * @param child
     * @return
     */
    @JsonIgnore
    Node<T> addChild(T child);

    /**
     * get the parent node
     *
     * @return
     */
    @JsonIgnore
    Node<T> getParent();

    /**
     * get the first child
     *
     * @return
     */
    @JsonIgnore
    Node<T> getFirstChild();

    /**
     * get the last child
     *
     * @return
     */
    @JsonIgnore
    Node<T> getLastChild();

    /**
     * unwrap to entity
     *
     * @return
     */
    @JsonIgnore
    T unwrap();

    /**
     * delete this node
     */
    @JsonIgnore
    void delete();

    @JsonIgnore
    boolean isRoot();

    /**
     * make this node as root with id
     *
     * @param newRootId
     */
    @JsonIgnore
    void makeRoot(Long newRootId);

    /**
     * is this node has any parent
     *
     * @return
     */
    @JsonIgnore
    boolean hasParent();

    /**
     * is this node has any children
     *
     * @return
     */
    @JsonIgnore
    boolean hasChildren();

    /**
     * is a valid node
     *
     * @return
     */
    @JsonIgnore
    boolean isValid();

    /**
     * whether it is a descendant of the node
     *
     * @param other
     * @return
     */
    @JsonIgnore
    boolean isDescendantOf(Node<T> other);
}
