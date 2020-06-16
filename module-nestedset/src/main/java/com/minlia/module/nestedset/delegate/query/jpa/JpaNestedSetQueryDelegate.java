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

package com.minlia.module.nestedset.delegate.query.jpa;

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

public abstract class JpaNestedSetQueryDelegate<ID extends Serializable, N extends NestedSet<ID>> {

    private final JpaTreeDiscriminator<ID, N> treeDiscriminator;

    protected final EntityManager entityManager;

    protected final Class<N> nodeClass;

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

    public JpaNestedSetQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, N> configuration) {
        this.entityManager = configuration.getEntityManager();
        this.treeDiscriminator = configuration.getTreeDiscriminator();
        this.nodeClass = configuration.getNodeClass();
        this.idClass = configuration.getIdClass();
//        this.customColumnNames=configuration.getCustomColumnNames();
        this.configs = configuration.getConfigs();
    }


    protected Predicate[] getPredicates(CriteriaBuilder cb, Root<N> root, Predicate... predicates) {
        List<Predicate> predicateList = new ArrayList<>(treeDiscriminator.getPredicates(cb, root));
        Collections.addAll(predicateList, predicates);
        return predicateList.toArray(new Predicate[0]);
    }
}
