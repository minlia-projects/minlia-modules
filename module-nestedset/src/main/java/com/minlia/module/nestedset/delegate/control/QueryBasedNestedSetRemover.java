package com.minlia.module.nestedset.delegate.control;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia, Inc
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

import com.minlia.module.nestedset.config.ConfigurationUtils;
import com.minlia.module.nestedset.delegate.NestedSetRemover;
import com.minlia.module.nestedset.delegate.query.NestedSetRemovingQueryDelegate;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import java.io.Serializable;


public class QueryBasedNestedSetRemover<ID extends Serializable, ENTITY extends NestedSet<ID>> implements NestedSetRemover<ID, ENTITY> {

    private final NestedSetRemovingQueryDelegate<ID, ENTITY> queryDelegate;

    public QueryBasedNestedSetRemover(NestedSetRemovingQueryDelegate<ID, ENTITY> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void removeSingle(NestedSetDetail<ID> nestedSetDetail) {
        Long from = nestedSetDetail.getRight();
        queryDelegate.setNewParentForDeletedNodesChildren(nestedSetDetail);
        queryDelegate.decrementSideFieldsBeforeSingleNodeRemoval(from, ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
        queryDelegate.decrementSideFieldsBeforeSingleNodeRemoval(from,  ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
        queryDelegate.pushUpDeletedNodesChildren(nestedSetDetail);
        queryDelegate.performSingleDeletion(nestedSetDetail);
    }

    @Override
    public void removeSubtree(NestedSetDetail<ID> nestedSetDetail) {
        Long delta = nestedSetDetail.getRight() - nestedSetDetail.getLeft() + 1;
        Long from = nestedSetDetail.getRight();
        queryDelegate.performBatchDeletion(nestedSetDetail);
        queryDelegate.decrementSideFieldsAfterSubtreeRemoval(from, delta,  ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
        queryDelegate.decrementSideFieldsAfterSubtreeRemoval(from, delta, ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
    }
}
