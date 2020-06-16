/*
 *  The MIT License
 *
 *  Copyright (c) 2019 eXsio.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 *  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 *  the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 *  BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.minlia.module.nestedset.delegate.control;

import com.minlia.module.nestedset.model.InMemoryTree;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.model.Tree;
import com.minlia.module.nestedset.delegate.NestedSetRetriever;
import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class QueryBasedNestedSetRetriever<ID extends Serializable, N extends NestedSet<ID>> implements NestedSetRetriever<ID, N> {

    private final NestedSetRetrievingQueryDelegate<ID, N> queryDelegate;

    public QueryBasedNestedSetRetriever(NestedSetRetrievingQueryDelegate<ID, N> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public Tree<ID, N> getTree(N node) {
        Tree<ID, N> tree = new InMemoryTree<>(node);
        for (N n : queryDelegate.getChildren(node)) {
            Tree<ID, N> subtree = this.getTree(n);
            tree.addChild(subtree);
        }
        return tree;
    }

    @Override
    public List<N> getTreeAsList(N node) {
        return queryDelegate.getTreeAsList(node);
    }

    @Override
    public List<N> getChildren(N node) {
        return queryDelegate.getChildren(node);
    }

    @Override
    public Optional<N> getParent(N node) {
        return queryDelegate.getParent(node);
    }

    @Override
    public List<N> getParents(N node) {
        return queryDelegate.getParents(node);
    }

    @Override
    public Optional<N> getPrevSibling(N node) {
        return queryDelegate.getPrevSibling(node);
    }

    @Override
    public Optional<N> getNextSibling(N node) {
        return queryDelegate.getNextSibling(node);
    }

    @Override
    public Optional<NestedSetDetail<ID>> getNodeInfo(ID nodeId) {
        return queryDelegate.getNodeInfo(nodeId);
    }

    @Override
    public Optional<N> findFirstRoot() {
        return queryDelegate.findFirstRoot();
    }

    @Override
    public Optional<N> findLastRoot() {
        return queryDelegate.findLastRoot();
    }

}
