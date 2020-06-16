
package com.minlia.module.nestedset.delegate.query.mem;

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

import com.google.common.base.Preconditions;
import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.delegate.query.NestedSetRebuildingQueryDelegate;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryNestedSetRebuildingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends InMemoryNestedNodeQueryDelegate<ID, N>
        implements NestedSetRebuildingQueryDelegate<ID, N> {

    public InMemoryNestedSetRebuildingQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public void destroyTree() {
        nodesStream()
                .forEach(n -> {
                    n.setLeft(0L);
                    n.setRight(0L);
                    n.setLevel(0L);
                });
    }

    @Override

    public N findFirst() {
        return nodesStream()
                .filter(n -> n.getParentId() == null)
                .max(getIdComparator()).orElseThrow(() -> new InvalidNodeException("There are no Root Nodes in the Tree"));
    }

    @Override
    public void resetFirst(N first) {
        nodesStream()
              .filter(n -> n.getId().equals(first.getId()))
              .forEach(n -> {
                  n.setLeft(1L);
                  n.setRight(2L);
                  n.setLevel(0L);
              });
    }

    @Override
    public List<N> getSiblings(ID first) {
        Preconditions.checkNotNull(first);
        return nodesStream()
                .filter(n -> getSerializable(NestedSet.PARENT_ID, n) == null)
                .filter(n -> !getSerializable(NestedSet.ID, n).equals(first))
                .sorted(getIdComparator())
                .collect(Collectors.toList());
    }

    @Override
    public List<N> getChildren(N parent) {
        return nodesStream()
                .filter(n -> parent.getId().equals(getSerializable(NestedSet.PARENT_ID, n)))
                .sorted(getIdComparator())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Comparator<N> getIdComparator() {
        return (o1, o2) -> {
            if (o1.getId() instanceof Comparable) {
                return ((Comparable) o1.getId()).compareTo(o2.getId());
            } else {
                return Integer.compare(o1.getId().hashCode(), o2.getId().hashCode());
            }
        };
    }
}
