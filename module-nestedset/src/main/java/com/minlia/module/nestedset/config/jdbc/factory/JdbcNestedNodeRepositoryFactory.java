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

package com.minlia.module.nestedset.config.jdbc.factory;

import com.minlia.module.nestedset.DelegatingNestedNodeRepository;
import com.minlia.module.nestedset.NestedNodeRepository;
import com.minlia.module.nestedset.delegate.control.*;
import com.minlia.module.nestedset.delegate.query.jdbc.*;
import com.minlia.module.nestedset.lock.NoLock;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;

import java.io.Serializable;

/**
 * Factory class to construct new instances of JDBC Tree Repositories.
 */
public final class JdbcNestedNodeRepositoryFactory {

    private JdbcNestedNodeRepositoryFactory() {
    }

    /**
     * Creates a new instance of NestedNodeRepository backed by JDBC storage without any Repository locking.
     *
     * @param configuration - JDBC Repository configuration
     * @param <ID> - Nested Node Identifier Class
     * @param <N> - Nested Node Class
     * @return - a new instance of NestedNodeRepository backed by JDBC storage
     */
    public static <ID extends Serializable, N extends NestedSet<ID>> NestedNodeRepository<ID, N> create(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        return create(configuration, new NoLock<>());
    }

    /**
     * Creates a new instance of NestedNodeRepository backed by JDBC storage with custom Repository locking.
     *
     * @param configuration - JDBC Repository configuration
     * @param lock - custom Repository Lock implementation
     * @param <ID> - Nested Node Identifier Class
     * @param <N> - Nested Node Class
     * @return - a new instance of NestedNodeRepository backed by JDBC storage
     */
    public static <ID extends Serializable, N extends NestedSet<ID>> NestedNodeRepository<ID, N> create(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration, NestedNodeRepository.Lock<ID, N> lock) {
        QueryBasedNestedSetCreator<ID, N> inserter = new QueryBasedNestedSetCreator<>(new JdbcNestedSetInsertingQueryDelegate<>(configuration));
        QueryBasedNestedSetRetriever<ID, N> retriever = new QueryBasedNestedSetRetriever<>(new JdbcNestedSetRetrievingQueryDelegate<>(configuration));
        return new DelegatingNestedNodeRepository<>(
                new QueryBasedNestedSetMover<>(new JdbcNestedSetMovingQueryDelegate<>(configuration)),
                new QueryBasedNestedSetRemover<>(new JdbcNestedSetRemovingQueryDelegate<>(configuration)),
                retriever,
                new QueryBasedNestedSetRebuilder<>(inserter, retriever, new JdbcNestedSetRebuildingQueryDelegate<>(configuration)),
                inserter,
                lock
        );

    }
}
