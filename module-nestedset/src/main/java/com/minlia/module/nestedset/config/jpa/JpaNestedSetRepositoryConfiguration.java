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

package com.minlia.module.nestedset.config.jpa;

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
