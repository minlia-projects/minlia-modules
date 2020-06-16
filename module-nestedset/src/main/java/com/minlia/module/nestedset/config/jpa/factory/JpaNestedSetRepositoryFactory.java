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

package com.minlia.module.nestedset.config.jpa.factory;

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
