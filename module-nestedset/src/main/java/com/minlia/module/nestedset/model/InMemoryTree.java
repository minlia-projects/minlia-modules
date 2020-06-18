package com.minlia.module.nestedset.model;

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

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class InMemoryTree<ID extends Serializable, N extends NestedSet<ID>> implements Tree<ID, N> {

    private List<Tree<ID, N>> children;

    private Tree<ID, N> parent;

    private N node;

    private InMemoryTree() {
        this.children = Lists.newLinkedList();
    }

    public InMemoryTree(N node) {
        this();
        this.node = node;
    }

    public InMemoryTree(N node, Tree<ID, N> parent) {
        this();
        this.parent = parent;
        this.node = node;
    }

    @Override
    public void addChild(Tree<ID, N> child) {
        this.children.add(child);
        child.setParent(this);
    }

    @Override
    public void setChildren(List<Tree<ID, N>> children) {
        this.children = children;
    }

    @Override
    public List<Tree<ID, N>> getChildren() {
        return this.children;
    }

    @Override
    public Tree<ID, N> getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Tree<ID, N> parent) {
        this.parent = parent;
    }

    @Override
    public N getNode() {
        return this.node;
    }

    @Override
    public void setNode(N node) {
        this.node = node;
    }

}
