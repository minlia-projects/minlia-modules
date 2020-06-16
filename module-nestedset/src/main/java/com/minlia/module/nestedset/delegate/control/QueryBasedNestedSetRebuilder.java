package com.minlia.module.nestedset.delegate.control;

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

public class QueryBasedNestedSetRebuilder<ID extends Serializable, ENTITY extends NestedSet<ID>> implements NestedSetRebuilder<ID, ENTITY> {

    private final NestedSetCreator<ID, ENTITY> inserter;

    private final NestedSetRetriever<ID, ENTITY> retriever;

    private final NestedSetRebuildingQueryDelegate<ID, ENTITY> queryDelegate;

    public QueryBasedNestedSetRebuilder(NestedSetCreator<ID, ENTITY> inserter, NestedSetRetriever<ID, ENTITY> retriever,
                                        NestedSetRebuildingQueryDelegate<ID, ENTITY> queryDelegate) {
        this.inserter = inserter;
        this.retriever = retriever;
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void rebuildTree() {
        ENTITY first = queryDelegate.findFirst();
        queryDelegate.resetFirst(first);
        restoreSiblings(first);
        rebuildRecursively(first);
        for (ENTITY node : queryDelegate.getSiblings(first.getId())) {
            rebuildRecursively(node);
        }
    }

    @Override
    public void destroyTree() {
        queryDelegate.destroyTree();
    }

    private void rebuildRecursively(ENTITY parent) {
        for (ENTITY child : queryDelegate.getChildren(parent)) {
            inserter.create(child, getNestedSetDetail(parent.getId()), NestedSetMover.Mode.LAST_CHILD);
            rebuildRecursively(child);
        }
    }

    private void restoreSiblings(ENTITY first) {
        for (ENTITY node : queryDelegate.getSiblings(first.getId())) {
            inserter.create(node, getNestedSetDetail(first.getId()), NestedSetMover.Mode.NEXT_SIBLING);
        }
    }

    private NestedSetDetail<ID> getNestedSetDetail(ID nodeId) {
        Preconditions.checkNotNull(nodeId);
        Optional<NestedSetDetail<ID>> nodeInfo = retriever.getNestedSetDetail(nodeId);
        if (!nodeInfo.isPresent()) {
            throw new InvalidNodeException(String.format("Couldn't find node with Id %s", nodeId));
        }
        return nodeInfo.get();
    }

}
