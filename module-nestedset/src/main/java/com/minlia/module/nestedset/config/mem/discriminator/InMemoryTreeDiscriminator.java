
package com.minlia.module.nestedset.config.mem.discriminator;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia, Inc
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

import java.io.Serializable;

/**
 * Tree Discriminator for use with InMemory Repository implementation.
 * Allows to store multiple intependant Trees in one Repository/Collection.
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public interface InMemoryTreeDiscriminator<ID extends Serializable, N extends NestedSet<ID>> {

    /**
     * Method that decides whether a target Node belongs to the Tree or not.
     *
     * @param node - target Node
     * @return true if Node belongs to the Tree, false if Node does not belong to the Tree
     */
    boolean applies(N node);
}
