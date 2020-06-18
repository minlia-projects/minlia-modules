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

import com.minlia.module.nestedset.model.InMemoryTree;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.model.Tree;
import com.minlia.module.nestedset.delegate.NestedSetRetriever;
import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class QueryBasedNestedSetRetriever<ID extends Serializable, ENTITY extends NestedSet<ID>> implements NestedSetRetriever<ID, ENTITY> {

    private final NestedSetRetrievingQueryDelegate<ID, ENTITY> queryDelegate;

    public QueryBasedNestedSetRetriever(NestedSetRetrievingQueryDelegate<ID, ENTITY> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public Tree<ID, ENTITY> getTree(ENTITY entity) {
        Tree<ID, ENTITY> tree = new InMemoryTree<>(entity);
        for (ENTITY n : queryDelegate.getChildren(entity)) {
            Tree<ID, ENTITY> subtree = this.getTree(n);
            tree.addChild(subtree);
        }
        return tree;
    }

    @Override
    public List<ENTITY> getTreeAsList(ENTITY entity) {
        return queryDelegate.getTreeAsList(entity);
    }

    @Override
    public List<ENTITY> getChildren(ENTITY entity) {
        return queryDelegate.getChildren(entity);
    }

    @Override
    public Optional<ENTITY> getParent(ENTITY entity) {
        return queryDelegate.getParent(entity);
    }

    @Override
    public List<ENTITY> getParents(ENTITY entity) {
        return queryDelegate.getParents(entity);
    }

    @Override
    public Optional<ENTITY> getPrevSibling(ENTITY entity) {
        return queryDelegate.getPrevSibling(entity);
    }

    @Override
    public Optional<ENTITY> getNextSibling(ENTITY entity) {
        return queryDelegate.getNextSibling(entity);
    }

    @Override
    public Optional<NestedSetDetail<ID>> getNestedSetDetail(ID nestedSetDetailIds) {
        return queryDelegate.getNestedSetDetail(nestedSetDetailIds);
    }

    @Override
    public Optional<ENTITY> findFirstRoot() {
        return queryDelegate.findFirstRoot();
    }

    @Override
    public Optional<ENTITY> findLastRoot() {
        return queryDelegate.findLastRoot();
    }

}
