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

package com.minlia.module.nestedset.delegate.query.mem;

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
