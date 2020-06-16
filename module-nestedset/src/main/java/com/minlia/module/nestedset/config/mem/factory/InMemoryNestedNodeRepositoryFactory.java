
package com.minlia.module.nestedset.config.mem.factory;

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

import com.minlia.module.nestedset.DelegatingNestedNodeRepository;
import com.minlia.module.nestedset.NestedNodeRepository;
import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.control.*;
import com.minlia.module.nestedset.delegate.query.mem.*;
import com.minlia.module.nestedset.lock.NoLock;
import com.minlia.module.nestedset.model.NestedSet;

import java.io.Serializable;

/**
 * Factory class to construct new instances of InMemory Tree Repositories.
 */
public final class InMemoryNestedNodeRepositoryFactory {

    private InMemoryNestedNodeRepositoryFactory() {
    }

    /**
     * Creates a new instance of NestedNodeRepository backed by InMemory storage without any Repository locking.
     *
     * @param configuration - InMemory Repository configuration
     * @param <ID> - Nested Node Identifier Class
     * @param <N> - Nested Node Class
     * @return - a new instance of NestedNodeRepository backed by InMemory storage
     */
    public static <ID extends Serializable, N extends NestedSet<ID>> NestedNodeRepository<ID, N> create(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        return create(configuration, new NoLock<>());
    }

    /**
     * Creates a new instance of NestedNodeRepository backed by InMemory storage with custom Repository locking.
     *
     * @param configuration - InMemory Repository configuration
     * @param lock - custom Repository Lock implementation
     * @param <ID> - Nested Node Identifier Class
     * @param <N> - Nested Node Class
     * @return - a new instance of NestedNodeRepository backed by InMemory storage
     */
    public static <ID extends Serializable, N extends NestedSet<ID>> NestedNodeRepository<ID, N> create(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration, NestedNodeRepository.Lock<ID, N> lock) {
        QueryBasedNestedSetCreator<ID, N> inserter = new QueryBasedNestedSetCreator<>(new InMemoryNestedSetInsertingQueryDelegate<>(configuration));
        QueryBasedNestedSetRetriever<ID, N> retriever = new QueryBasedNestedSetRetriever<>(new InMemoryNestedSetRetrievingQueryDelegate<>(configuration));
        return new DelegatingNestedNodeRepository<>(
                new QueryBasedNestedSetMover<>(new InMemoryNestedSetMovingQueryDelegate<>(configuration)),
                new QueryBasedNestedSetRemover<>(new InMemoryNestedSetRemovingQueryDelegate<>(configuration)),
                retriever,
                new QueryBasedNestedSetRebuilder<>(inserter, retriever, new InMemoryNestedSetRebuildingQueryDelegate<>(configuration)),
                inserter,
                lock
        );
    }
}
