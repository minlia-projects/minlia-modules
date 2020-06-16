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
import com.minlia.module.nestedset.delegate.query.NestedSetInsertingQueryDelegate;

import java.io.Serializable;


public class InMemoryNestedSetInsertingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends InMemoryNestedNodeQueryDelegate<ID, N>
        implements NestedSetInsertingQueryDelegate<ID, N> {


    public InMemoryNestedSetInsertingQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }

    @Override
    public Class<?> getClz() {
        return null;
    }

    @Override
    public void insert(N node) {
        if (node.getId() == null) {
            doInsert(node);
        } else {
            update(node);
        }
    }

    private void update(N node) {
        nodesStream()
                .filter(n -> getSerializable(NestedSet.ID, n).equals(node.getId()))
                .forEach(n -> {
                    n.setLevel(node.getLevel());
                    n.setLeft(node.getLeft());
                    n.setRight(node.getRight());
                    n.setParentId(node.getParentId());
                });
    }

    private void doInsert(N node) {
        ID newId = generateIdentity();
        node.setId(newId);
        nodes.add(node);
    }

    @Override
    public void incrementSideFieldsGreaterThan(Long from, String fieldName) {
        nodesStream()
                .filter(n -> getLong(fieldName, n) > from)
                .forEach(n -> setLong(fieldName, n, getLong(fieldName, n) + INCREMENT_BY));
    }

    @Override
    public void incermentSideFieldsGreaterThanOrEqualTo(Long from, String fieldName) {
        nodesStream()
                .filter(n -> getLong(fieldName, n) >= from)
                .forEach(n -> setLong(fieldName, n, getLong(fieldName, n) + INCREMENT_BY));
    }

}
