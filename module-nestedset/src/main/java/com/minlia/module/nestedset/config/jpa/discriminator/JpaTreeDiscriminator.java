
package com.minlia.module.nestedset.config.jpa.discriminator;

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

import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * Tree Discriminator for use with JPA Repository implementation.
 * Allows to store multiple intependant Trees in one Repository/Collection.
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public interface JpaTreeDiscriminator<ID extends Serializable, N extends NestedSet<ID>> {

    /**
     * Method that decides wich Nodes should be affected by the Criteria Query.
     *
     * @param cb - JPA Criteria Builder
     * @param root - JPA Criteria Root of the Query
     * @return - List of JPA Criteria Predicates to be added to every Criteria Query executed by the JPA implementation
     */
    List<Predicate> getPredicates(CriteriaBuilder cb, Root<N> root);
}
