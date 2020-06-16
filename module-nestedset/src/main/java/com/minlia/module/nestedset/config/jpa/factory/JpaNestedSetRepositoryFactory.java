
package com.minlia.module.nestedset.config.jpa.factory;

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
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.control.*;
import com.minlia.module.nestedset.delegate.query.jpa.*;
import com.minlia.module.nestedset.lock.NoLock;
import com.minlia.module.nestedset.model.NestedSet;

import java.io.Serializable;

/**
 * Factory class to construct new instances of JPA Tree Repositories.
 */
public final class JpaNestedSetRepositoryFactory {

    private JpaNestedSetRepositoryFactory() {
    }

    /**
     * Creates a new instance of NestedNodeRepository backed by JPA storage without any Repository locking.
     *
     * @param configuration - JPA Repository configuration
     * @param <ID>          - Nested Node Identifier Class
     * @param <N>           - Nested Node Class
     * @return - a new instance of NestedNodeRepository backed by JPA storage
     */
    public static <ID extends Serializable, N extends NestedSet<ID>> NestedNodeRepository<ID, N> create(JpaNestedSetRepositoryConfiguration<ID, N> configuration) {
        return create(configuration, new NoLock<>());
    }

    /**
     * Creates a new instance of NestedNodeRepository backed by JPA storage with custom Repository locking.
     *
     * @param configuration - JPA Repository configuration
     * @param lock          - custom Repository Lock implementation
     * @param <ID>          - Nested Node Identifier Class
     * @param <N>           - Nested Node Class
     * @return - a new instance of NestedNodeRepository backed by JPA storage
     */
    public static <ID extends Serializable, N extends NestedSet<ID>> NestedNodeRepository<ID, N> create(JpaNestedSetRepositoryConfiguration<ID, N> configuration, NestedNodeRepository.Lock<ID, N> lock) {
        QueryBasedNestedSetCreator<ID, N> inserter = new QueryBasedNestedSetCreator<>(new JpaNestedSetInsertingQueryDelegate<>(configuration));
        QueryBasedNestedSetRetriever<ID, N> retriever = new QueryBasedNestedSetRetriever<>(new JpaNestedSetRetrievingQueryDelegate<>(configuration));
        return new DelegatingNestedNodeRepository<>(
                new QueryBasedNestedSetMover<>(new JpaNestedSetMovingQueryDelegate<>(configuration)),
                new QueryBasedNestedSetRemover<>(new JpaNestedSetRemovingQueryDelegate<>(configuration)),
                retriever,
                new QueryBasedNestedSetRebuilder<>(inserter, retriever, new JpaNestedSetRebuildingQueryDelegate<>(configuration)),
                inserter,
                lock
        );
    }
}
