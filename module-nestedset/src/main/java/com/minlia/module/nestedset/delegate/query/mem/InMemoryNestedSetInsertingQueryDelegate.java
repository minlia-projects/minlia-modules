
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
    public void insert(N entity) {
        if (entity.getId() == null) {
            doInsert(entity);
        } else {
            update(entity);
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
