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

import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryNestedSetRetrievingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends InMemoryNestedNodeQueryDelegate<ID, N>
        implements NestedSetRetrievingQueryDelegate<ID, N> {

    public InMemoryNestedSetRetrievingQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public List<N> getTreeAsList(N node) {
        return nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) >= node.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) <= node.getRight())
                .sorted(Comparator.comparing(NestedSet::getLeft))
                .collect(Collectors.toList());
    }

    @Override
    public List<N> getChildren(N node) {
        return nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) >= node.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) <= node.getRight())
                .filter(n -> getLong(NestedSet.LEVEL, n).equals(node.getLevel() + 1))
                .sorted(Comparator.comparing(NestedSet::getLeft))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<N> getParent(N node) {
        if (node.getLevel() > 0) {
            return nodesStream()
                    .filter(n -> getLong(NestedSet.LEFT, n) < node.getLeft())
                    .filter(n -> getLong(NestedSet.RIGHT, n) > node.getRight())
                    .filter(n -> getLong(NestedSet.LEVEL, n).equals(node.getLevel() - 1))
                    .min(Comparator.comparing(NestedSet::getLeft));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<N> getParents(N node) {
        return nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n) < node.getLeft())
                .filter(n -> getLong(NestedSet.RIGHT, n) > node.getRight())
                .sorted(Comparator.<N, Long>comparing(NestedSet::getLeft).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<N> getPrevSibling(N node) {
        return nodesStream()
                .filter(n -> getLong(NestedSet.RIGHT, n).equals(node.getLeft() - 1))
                .filter(n -> getLong(NestedSet.LEVEL, n).equals(node.getLevel()))
                .min(Comparator.comparing(NestedSet::getLeft));
    }

    @Override
    public Optional<N> getNextSibling(N node) {
        return nodesStream()
                .filter(n -> getLong(NestedSet.LEFT, n).equals(node.getRight() + 1))
                .filter(n -> getLong(NestedSet.LEVEL, n).equals(node.getLevel()))
                .min(Comparator.comparing(NestedSet::getLeft));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<NestedSetDetail<ID>> getNodeInfo(ID nodeId) {
        Optional<N> node = nodesStream()
                .filter(n -> getSerializable(NestedSet.ID, n).equals(nodeId))
                .findFirst();
        return node.map(n -> new NestedSetDetail<>(
                        (ID) getSerializable(NestedSet.ID, n),
                        (ID) getSerializable(NestedSet.PARENT_ID, n),
                        getLong(NestedSet.LEFT, n),
                        getLong(NestedSet.RIGHT, n),
                        getLong(NestedSet.LEVEL, n)
                ));
    }

    @Override
    public Optional<N> findFirstRoot() {
        return nodesStream()
                .filter(n -> getLong(NestedSet.LEVEL, n).equals(0L))
                .min(Comparator.comparing(NestedSet::getLeft));
    }

    @Override
    public Optional<N> findLastRoot() {
        return nodesStream()
                .filter(n -> getLong(NestedSet.LEVEL, n).equals(0L))
                .max(Comparator.comparing(NestedSet::getLeft));
    }
}
