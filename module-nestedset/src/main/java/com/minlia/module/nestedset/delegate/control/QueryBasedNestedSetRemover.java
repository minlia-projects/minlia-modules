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

import com.minlia.module.nestedset.config.ConfigurationUtils;
import com.minlia.module.nestedset.delegate.NestedSetRemover;
import com.minlia.module.nestedset.delegate.query.NestedSetRemovingQueryDelegate;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import java.io.Serializable;


public class QueryBasedNestedSetRemover<ID extends Serializable, N extends NestedSet<ID>> implements NestedSetRemover<ID, N> {

    private final NestedSetRemovingQueryDelegate<ID, N> queryDelegate;

    public QueryBasedNestedSetRemover(NestedSetRemovingQueryDelegate<ID, N> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void removeSingle(NestedSetDetail<ID> nodeInfo) {
        Long from = nodeInfo.getRight();
        queryDelegate.setNewParentForDeletedNodesChildren(nodeInfo);
        queryDelegate.decrementSideFieldsBeforeSingleNodeRemoval(from, ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
        queryDelegate.decrementSideFieldsBeforeSingleNodeRemoval(from,  ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
        queryDelegate.pushUpDeletedNodesChildren(nodeInfo);
        queryDelegate.performSingleDeletion(nodeInfo);
    }

    @Override
    public void removeSubtree(NestedSetDetail<ID> nodeInfo) {
        Long delta = nodeInfo.getRight() - nodeInfo.getLeft() + 1;
        Long from = nodeInfo.getRight();
        queryDelegate.performBatchDeletion(nodeInfo);
        queryDelegate.decrementSideFieldsAfterSubtreeRemoval(from, delta,  ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
        queryDelegate.decrementSideFieldsAfterSubtreeRemoval(from, delta, ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
    }
}
