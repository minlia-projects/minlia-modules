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

import com.google.common.base.Preconditions;
import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.delegate.NestedSetCreator;
import com.minlia.module.nestedset.delegate.NestedSetMover;
import com.minlia.module.nestedset.delegate.NestedSetRebuilder;
import com.minlia.module.nestedset.delegate.NestedSetRetriever;
import com.minlia.module.nestedset.delegate.query.NestedSetRebuildingQueryDelegate;

import java.io.Serializable;
import java.util.Optional;

public class QueryBasedNestedSetRebuilder<ID extends Serializable, N extends NestedSet<ID>> implements NestedSetRebuilder<ID, N> {

    private final NestedSetCreator<ID, N> inserter;

    private final NestedSetRetriever<ID, N> retriever;

    private final NestedSetRebuildingQueryDelegate<ID, N> queryDelegate;

    public QueryBasedNestedSetRebuilder(NestedSetCreator<ID, N> inserter, NestedSetRetriever<ID, N> retriever,
                                        NestedSetRebuildingQueryDelegate<ID, N> queryDelegate) {
        this.inserter = inserter;
        this.retriever = retriever;
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void rebuildTree() {
        N first = queryDelegate.findFirst();
        queryDelegate.resetFirst(first);
        restoreSiblings(first);
        rebuildRecursively(first);
        for (N node : queryDelegate.getSiblings(first.getId())) {
            rebuildRecursively(node);
        }
    }

    @Override
    public void destroyTree() {
        queryDelegate.destroyTree();
    }

    private void rebuildRecursively(N parent) {
        for (N child : queryDelegate.getChildren(parent)) {
            inserter.create(child, getNodeInfo(parent.getId()), NestedSetMover.Mode.LAST_CHILD);
            rebuildRecursively(child);
        }
    }

    private void restoreSiblings(N first) {
        for (N node : queryDelegate.getSiblings(first.getId())) {
            inserter.create(node, getNodeInfo(first.getId()), NestedSetMover.Mode.NEXT_SIBLING);
        }
    }

    private NestedSetDetail<ID> getNodeInfo(ID nodeId) {
        Preconditions.checkNotNull(nodeId);
        Optional<NestedSetDetail<ID>> nodeInfo = retriever.getNodeInfo(nodeId);
        if (!nodeInfo.isPresent()) {
            throw new InvalidNodeException(String.format("Couldn't find node with Id %s", nodeId));
        }
        return nodeInfo.get();
    }

}
