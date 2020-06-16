
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

import com.minlia.module.nestedset.annotation.*;
import com.minlia.module.nestedset.config.Configuration;
import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.config.jpa.discriminator.JpaTreeDiscriminator;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

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
        return getConfig(nodeClass).getLeftFieldName();
    }

    protected String getRightFieldName() {
        return getConfig(nodeClass).getRightFieldName();
    }

    protected String getLevelFieldName() {
        return getConfig(nodeClass).getLevelFieldName();
    }

    protected String getIdFieldName() {
        return getConfig(nodeClass).getIdFieldName();
    }

    protected String getParentIdFieldName() {
        return getConfig(nodeClass).getParentIdFieldName();
    }

    Configuration getConfig(Class<?> clazz) {
        if (!this.configs.containsKey(clazz)) {
            Configuration config = new Configuration();

            Entity entity = clazz.getAnnotation(Entity.class);
            String name = entity.name();
            config.setEntityName((name != null && name.length() > 0) ? name : clazz.getSimpleName());

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getAnnotation(LeftColumn.class) != null) {
                    config.setLeftFieldName(field.getName());
                } else if (field.getAnnotation(RightColumn.class) != null) {
                    config.setRightFieldName(field.getName());
                } else if (field.getAnnotation(LevelColumn.class) != null) {
                    config.setLevelFieldName(field.getName());
                } else if (field.getAnnotation(IdColumn.class) != null) {
                    config.setIdFieldName(field.getName());
                } else if (field.getAnnotation(ParentIdColumn.class) != null) {
                    config.setParentIdFieldName(field.getName());
                }
            }

            this.configs.put(clazz, config);
        }

        return this.configs.get(clazz);
    }

    public JpaNestedSetQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        this.entityManager = configuration.getEntityManager();
        this.treeDiscriminator = configuration.getTreeDiscriminator();
        this.nodeClass = configuration.getNodeClass();
        this.idClass = configuration.getIdClass();
//        this.customColumnNames=configuration.getCustomColumnNames();
        this.configs = configuration.getConfigs();
    }


    protected Predicate[] getPredicates(CriteriaBuilder cb, Root<ENTITY> root, Predicate... predicates) {
        List<Predicate> predicateList = new ArrayList<>(treeDiscriminator.getPredicates(cb, root));
        Collections.addAll(predicateList, predicates);
        return predicateList.toArray(new Predicate[0]);
    }
}
