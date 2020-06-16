
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Default implementation of JPA Tree Discriminator based on Map of <String, Supplier<Object>>
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public class MapJpaTreeDiscriminator<ID extends Serializable, N extends NestedSet<ID>> implements JpaTreeDiscriminator<ID, N> {

    private Map<String, Supplier<Object>> valueProviders;

    /**
     * Creates a JPA Tree Discriminator that will add Predicates to all the Criteria Queries based on the passed Map.
     * Keys of the Map will be added as Keys in the Criteria Predicates.
     * Values obtained by the Suppliers will be added as Values of corresponding Keys in the Criteria Predicates.
     *
     * @param valueProviders - Map of <String, Supplier<Object>>
     */
    public MapJpaTreeDiscriminator(Map<String, Supplier<Object>> valueProviders) {
        this.valueProviders = valueProviders;
    }

    /**
     * Create no-up Tree Discriminator
     */
    public MapJpaTreeDiscriminator() {
        this.valueProviders = Maps.newHashMap();
    }

    /**
     * Creates a JPA Tree Discriminator that will add Predicates to all the Criteria Queries based on the passed Map.
     * Keys of the Map will be added as Keys in the Criteria Predicates.
     * Values obtained by the Suppliers will be added as Values of corresponding Keys in the Criteria Predicates.
     *
     * @param valueProviders - Map of <String, Supplier<Object>>
     */
    public void setValueProviders(Map<String, Supplier<Object>> valueProviders) {
        this.valueProviders = valueProviders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<N> root) {
        List<Predicate> predicates = Lists.newArrayList();
        for (Map.Entry<String, Supplier<Object>> providerEntry : valueProviders.entrySet()) {
            predicates.add(cb.equal(root.get(providerEntry.getKey()), providerEntry.getValue().get()));
        }
        return predicates;
    }
}
