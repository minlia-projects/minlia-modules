
package com.minlia.module.nestedset.delegate.query.jpa;

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

import com.minlia.module.nestedset.config.jpa.JpaNestedSetRepositoryConfiguration;
import com.minlia.module.nestedset.model.NestedSet;
import org.springframework.transaction.annotation.Transactional;
import com.minlia.module.nestedset.delegate.query.NestedSetInsertingQueryDelegate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public class JpaNestedSetInsertingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>>
        extends JpaNestedSetQueryDelegate<ID, ENTITY>
        implements NestedSetInsertingQueryDelegate<ID, ENTITY> {

    public JpaNestedSetInsertingQueryDelegate(JpaNestedSetRepositoryConfiguration<ID, ENTITY> configuration) {
        super(configuration);
    }

    @Transactional
    @Override
    public void insert(ENTITY entity) {
        entityManager.persist(entity);
    }

    @Override
    public void incrementSideFieldsGreaterThan(Long from, String fieldName) {
        updateFields(from, fieldName, false);
    }

    @Override
    public void incermentSideFieldsGreaterThanOrEqualTo(Long from, String fieldName) {
        updateFields(from, fieldName, true);
    }

    private void updateFields(Long from, String fieldName, boolean gte) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ENTITY> update = cb.createCriteriaUpdate(nodeClass);
        Root<ENTITY> root = update.from(nodeClass);
        update.set(root.<Long>get(fieldName), cb.sum(root.get(fieldName), INCREMENT_BY));
        if (gte) {
            update.where(getPredicates(cb, root, cb.greaterThanOrEqualTo(root.get(fieldName), from)));
        } else {
            update.where(getPredicates(cb, root, cb.greaterThan(root.get(fieldName), from)));
        }
        entityManager.createQuery(update).executeUpdate();
    }
}
