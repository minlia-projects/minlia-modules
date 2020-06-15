package com.minlia.module.nestedset.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * An in-memory tree view of a list of {@link Node}s.
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = false, allowSetters = false)
public class NodeTreeView<T extends NodeDetail> implements Serializable {
    private static final Long serialVersionUID = 4L;

    public final T node;
    private NodeTreeView<T> parent;
    private final List<NodeTreeView<T>> children;

    private NodeTreeView(T node) {
        this.node = node;
        this.children = new ArrayList<>();
    }

    @JsonIgnore
    public NodeTreeView<T> getParent() {
        return this.parent;
    }

    @JsonIgnore
    public List<NodeTreeView<T>> getChildren() {
        return this.children;
    }

    @JsonIgnore
    public static <T extends NodeDetail> NodeTreeView<T> build(List<T> nodes) {
        return build(nodes, -1);
    }

    // nodes must be non-empty
    // nodes must be sorted by {@link NodeInfo#getLeftValue}
    @JsonIgnore
    public static <T extends NodeDetail> NodeTreeView<T> build(List<T> nodes, int maxLevel) {
        NodeTreeView<T> root = new NodeTreeView<>(nodes.get(0));
        Stack<NodeTreeView<T>> ancestors = new Stack<>();
        ancestors.push(root);

        Long level = root.node.getLevel();
        int total = nodes.size();
        for (int i = 1; i < total; ++i) {
            T node = nodes.get(i);
            NodeTreeView<T> tree = new NodeTreeView<>(node);
            while (ancestors.peek().node.getLevel() >= node.getLevel()) {
                ancestors.pop();
            }
            level = node.getLevel();

            NodeTreeView<T> parent = ancestors.peek();
            tree.parent = parent;
            parent.children.add(tree);

            boolean hasChildren = node.getRightValue() - node.getLeftValue() > 1;
            if (hasChildren && (maxLevel == -1 || maxLevel > level)) {
                ancestors.push(tree);
            }
        }

        return root;
    }
}

