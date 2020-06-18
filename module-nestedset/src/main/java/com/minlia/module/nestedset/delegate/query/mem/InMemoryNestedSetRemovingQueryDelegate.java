
package com.minlia.module.nestedset.delegate.query.mem;

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

import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.delegate.query.NestedSetRemovingQueryDelegate;

import java.io.Serializable;
import java.util.Optional;

public class InMemoryNestedSetRemovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends InMemoryNestedNodeQueryDelegate<ID, N>
        implements NestedSetRemovingQueryDelegate<ID, N> {

    public InMemoryNestedSetRemovingQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public Class<?> getClz() {
        return null;
    }

    @Override
    public void setNewParentForDeletedNodesChildren(NestedSetDetail<ID> nestedSetDetail) {
        nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) >= nestedSetDetail.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) <= nestedSetDetail.getRight())
                .filter(n -> getLong(NestedSet.LEVEL, n).equals(nestedSetDetail.getLevel() + 1))
                .forEach(n -> setSerializable(NestedSet.PARENT_ID, n, findNodeParentId(nestedSetDetail).orElse(null)));
    }

    @Override
    public void performSingleDeletion(NestedSetDetail<ID> nestedSetDetail) {
        nodesStream()
                .filter(n -> getSerializable(NestedSet.ID, n).equals(nestedSetDetail.getId()))
                .forEach(nodes::remove);
    }

    @Override
    public void decrementSideFieldsBeforeSingleNodeRemoval(Long from, String field) {
        decrementSideFields(from, DECREMENT_BY, field);
    }

    @Override
    public void pushUpDeletedNodesChildren(NestedSetDetail<ID> nestedSetDetail) {
        nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) > nestedSetDetail.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) < nestedSetDetail.getRight())
                .forEach(n -> {
                    setLong(NestedSet.RIGHT, n , getLong(NestedSet.RIGHT, n) - 1);
                    setLong(NestedSet.LEFT, n , getLong(NestedSet.LEFT, n) - 1);
                    setLong(NestedSet.LEVEL, n , getLong(NestedSet.LEVEL, n) - 1);
                });
    }

    @Override
    public void decrementSideFieldsAfterSubtreeRemoval(Long from, Long delta, String field) {
        decrementSideFields(from, delta, field);
    }

    @Override
    public void performBatchDeletion(NestedSetDetail<ID> nestedSetDetail) {
        nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) >= nestedSetDetail.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) <= nestedSetDetail.getRight())
                .forEach(nodes::remove);
    }

    private void decrementSideFields(Long from, Long delta, String field) {
        nodesStream()
                .filter(n -> getLong(field, n) > from)
                .forEach(n -> setLong(field, n , getLong(field, n) - delta));
    }

    private Optional<ID> findNodeParentId(NestedSetDetail<ID> node) {
        if (node.getLevel() > 0) {
            return Optional.of(nodesStream()
                    .filter(n -> getLong(NestedSet.LEFT, n) < node.getLeft())
                    .filter(n -> getLong(NestedSet.RIGHT, n) > node.getRight())
                    .filter(n -> getLong(NestedSet.LEVEL, n).equals(node.getLevel() - 1))
                    .map(NestedSet::getId)
                    .findFirst()
                    .orElseThrow(() -> new InvalidNodeException(String.format("Couldn't find node's parent, although its level is greater than 0. It seems the tree is malformed: %s", node))));
        }
        return Optional.empty();
    }
}
