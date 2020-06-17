
package com.minlia.module.nestedset.delegate.query.jpa;

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

import com.minlia.module.nestedset.config.Configuration;
import com.minlia.module.nestedset.config.ConfigurationUtils;
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.config.jpa.discriminator.JpaTreeDiscriminator;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class JpaNestedSetQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>> {

    private final JpaTreeDiscriminator<ID, ENTITY> treeDiscriminator;

    protected final EntityManager entityManager;

    protected final Class<ENTITY> nodeClass;

    protected final Class<ID> idClass;

    private Map<Class<?>, Configuration> configs;


    public Class<?> getClz() {
        return nodeClass;
    }

    protected String getLeftFieldName() {
        return ConfigurationUtils.getLeftFieldName(nodeClass);
    }

    protected String getRightFieldName() {
        return ConfigurationUtils.getRightFieldName(nodeClass);
    }

    protected String getLevelFieldName() {
        return ConfigurationUtils.getLevelFieldName(nodeClass);
    }

    protected String getIdFieldName() {
        return ConfigurationUtils.getIdFieldName(nodeClass);
    }

    protected String getParentIdFieldName() {
        return ConfigurationUtils.getParentIdFieldName(nodeClass);
    }

    public JpaNestedSetQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        this.entityManager = configuration.getEntityManager();
        this.treeDiscriminator = configuration.getTreeDiscriminator();
        this.nodeClass = configuration.getNodeClass();
        this.idClass = configuration.getIdClass();
        this.configs = configuration.getConfigs();
    }


    protected Predicate[] getPredicates(CriteriaBuilder cb, Root<ENTITY> root, Predicate... predicates) {
        List<Predicate> predicateList = new ArrayList<>(treeDiscriminator.getPredicates(cb, root));
        Collections.addAll(predicateList, predicates);
        return predicateList.toArray(new Predicate[0]);
    }
}
