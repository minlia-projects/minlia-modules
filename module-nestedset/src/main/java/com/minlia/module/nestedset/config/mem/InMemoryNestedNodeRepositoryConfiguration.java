
package com.minlia.module.nestedset.config.mem;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minlia.module.nestedset.config.mem.discriminator.InMemoryTreeDiscriminator;
import com.minlia.module.nestedset.config.mem.identity.InMemoryNestedNodeIdentityGenerator;
import com.minlia.module.nestedset.model.NestedSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Configuration class that serves as a base of creating new instances of InMemory Repository.
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public class InMemoryNestedNodeRepositoryConfiguration<ID extends Serializable, N extends NestedSet<ID>> {

    private final InMemoryTreeDiscriminator<ID, N> treeDiscriminator;

    private final InMemoryNestedNodeIdentityGenerator<ID> identityGenerator;

    private final Set<N> nodes = Sets.newConcurrentHashSet();

    /**
     * Creates new InMemory Repository with empty Tree and no Tree Discriminator.
     *
     * @param identityGenerator - Identity generator used for inserting new Nodes into an InMemory Repository.
     */
    public InMemoryNestedNodeRepositoryConfiguration(InMemoryNestedNodeIdentityGenerator<ID> identityGenerator) {
        this(identityGenerator, Lists.newArrayList(), null);
    }

    /**
     * Creates new InMemory Repository with a collection of Nodes and no Tree Discriminator.
     * If the Nodes do not have proper LEFT/RIGHT/LEVEL values, Tree can be initialized with NestedNodeRepository::rebuildTree() method.
     *
     * @param identityGenerator - Identity generator used for inserting new Nodes into an InMemory Repository.
     * @param nodes - initial collection of Nodes
     */
    public InMemoryNestedNodeRepositoryConfiguration(InMemoryNestedNodeIdentityGenerator<ID> identityGenerator, Collection<N> nodes) {
       this(identityGenerator, nodes, null);
    }

    /**
     * Creates new InMemory Repository with a collection of Nodes and custom Tree Discriminator.
     * If the Nodes do not have proper LEFT/RIGHT/LEVEL values, Tree can be initialized with NestedNodeRepository::rebuildTree() method.
     *
     * @param identityGenerator - Identity generator used for inserting new Nodes into an InMemory Repository.
     * @param nodes - initial collection of Nodes
     * @param treeDiscriminator - custom Tree Discriminator
     */
    public InMemoryNestedNodeRepositoryConfiguration(InMemoryNestedNodeIdentityGenerator<ID> identityGenerator, Collection<N> nodes, InMemoryTreeDiscriminator<ID, N> treeDiscriminator) {
        this.identityGenerator = identityGenerator;
        this.treeDiscriminator = treeDiscriminator;
        this.nodes.addAll(nodes);
    }

    /**
     * @return Tree Discriminator used by this Configuration
     */
    public InMemoryTreeDiscriminator<ID, N> getTreeDiscriminator() {
        return treeDiscriminator;
    }

    /**
     * @return Identiy Generator used by this Configuration
     */
    public InMemoryNestedNodeIdentityGenerator<ID> getIdentityGenerator() {
        return identityGenerator;
    }

    /**
     *
     * This method can be used to retrieve the data structure backing the InMemory Repository.
     * You can store the collection to (no)SQL storage or use it for custom data retrieval logic.
     * It is not recommended to manually modify the LEFT/RIGHT/LEVEL values of the Nodes contained in the returned Set.
     *
     * @return flat Set of Nodes - the data structure backing the InMemory implementation.
     */
    public Set<N> getNodes() {
        return nodes;
    }
}
