
package com.minlia.module.nestedset.config.jpa;

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

import com.minlia.module.nestedset.config.Configuration;
import com.minlia.module.nestedset.config.jpa.discriminator.JpaTreeDiscriminator;
import com.minlia.module.nestedset.config.jpa.discriminator.MapJpaTreeDiscriminator;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class that serves as a base of creating new instances of JPA Repository.
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N>  - Nested Node Class
 */
public class JpaNestedSetRepositoryConfiguration<ID extends Serializable, N extends NestedSet<ID>> {

    private final JpaTreeDiscriminator<ID, N> treeDiscriminator;

    private final EntityManager entityManager;

    private final Class<N> nodeClass;

    private final Class<ID> idClass;

    private  Map<Class<?>, Configuration> configs;

    public Map<Class<?>, Configuration> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<Class<?>, Configuration> configs) {
        this.configs = configs;
    }

    /**
     * Creates new JPA Repository with custom Tree Discriminator.
     *
     * @param entityManager     - JPA Entity Manager to be used by the Repository
     * @param nodeClass         - Nested Node Identifier Class
     * @param idClass           - Nested Node Class
     * @param treeDiscriminator - custom Tree Discriminator
     */
    public JpaNestedSetRepositoryConfiguration(
            EntityManager entityManager, Class<N> nodeClass, Class<ID> idClass, JpaTreeDiscriminator<ID, N> treeDiscriminator,Map<Class<?>, Configuration> configs ) {
        this.treeDiscriminator = treeDiscriminator;
        this.entityManager = entityManager;
        this.nodeClass = nodeClass;
        this.idClass = idClass;
        this.configs=configs;
    }

    public JpaNestedSetRepositoryConfiguration(
            EntityManager entityManager, Class<N> nodeClass, Class<ID> idClass, JpaTreeDiscriminator<ID, N> treeDiscriminator) {
        this.treeDiscriminator = treeDiscriminator;
        this.entityManager = entityManager;
        this.nodeClass = nodeClass;
        this.idClass = idClass;
        this.configs = new HashMap<Class<?>, Configuration>();
    }

    /**
     * Creates new JPA Repository with no Tree Discriminator.
     *
     * @param entityManager - JPA Entity Manager to be used by the Repository
     * @param nodeClass     - Nested Node Identifier Class
     * @param idClass       - Nested Node Class
     */
    public JpaNestedSetRepositoryConfiguration(EntityManager entityManager, Class<N> nodeClass, Class<ID> idClass) {
        this(entityManager, nodeClass, idClass, new MapJpaTreeDiscriminator<>());
    }

    /**
     * @return Tree Discriminator used by this Configuration
     */
    public JpaTreeDiscriminator<ID, N> getTreeDiscriminator() {
        return treeDiscriminator;
    }

    /**
     * @return JPA Entity Manager used by this Configuration
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @return Node Class used by this Configuration
     */
    public Class<N> getNodeClass() {
        return nodeClass;
    }

    /**
     * @return Node Identifier Class used by this Configuration
     */
    public Class<ID> getIdClass() {
        return idClass;
    }


}
