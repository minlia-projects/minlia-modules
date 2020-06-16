package com.minlia.module.nestedset.delegate.control;

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
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.config.ConfigurationUtils;
import com.minlia.module.nestedset.delegate.NestedSetCreator;
import com.minlia.module.nestedset.delegate.query.NestedSetInsertingQueryDelegate;

import java.io.Serializable;
import java.util.Optional;

public class QueryBasedNestedSetCreator<ID extends Serializable, ENTITY extends NestedSet<ID>> implements NestedSetCreator<ID, ENTITY> {

    private final NestedSetInsertingQueryDelegate<ID, ENTITY> queryDelegate;

    public QueryBasedNestedSetCreator(NestedSetInsertingQueryDelegate<ID, ENTITY> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void create(ENTITY entity, NestedSetDetail<ID> parentNestedSetDetail, Mode mode) {
        makeSpaceForNewElement(getMoveFrom(parentNestedSetDetail, mode), mode);
        insertNodeIntoTree(parentNestedSetDetail, entity, mode);
    }

    @Override
    public void createAsFirstNode(ENTITY entity) {
        entity.setLeft(1L);
        entity.setRight(2L);
        entity.setLevel(0L);
        entity.setParentId(null);
        queryDelegate.insert(entity);
    }

    private void insertNodeIntoTree(NestedSetDetail<ID> parent, ENTITY entity, Mode mode) {
        Long left = this.getNodeLeft(parent, mode);
        Long right = left + 1;
        Long level = this.getNodeLevel(parent, mode);
        entity.setLeft(left);
        entity.setRight(right);
        entity.setLevel(level);
        entity.setParentId(this.getNodeParent(parent, mode).orElse(null));
        queryDelegate.insert(entity);
    }

    private void makeSpaceForNewElement(Long from, Mode mode) {
        if (applyGte(mode)) {
            queryDelegate.incermentSideFieldsGreaterThanOrEqualTo(from, ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
            queryDelegate.incermentSideFieldsGreaterThanOrEqualTo(from, ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
        } else {
            queryDelegate.incrementSideFieldsGreaterThan(from, ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
            queryDelegate.incrementSideFieldsGreaterThan(from, ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
        }

    }

    private Long getMoveFrom(NestedSetDetail<ID> parent, Mode mode) {
        switch (mode) {
            case PREV_SIBLING:
            case FIRST_CHILD:
                return parent.getLeft();
            case NEXT_SIBLING:
            case LAST_CHILD:
            default:
                return parent.getRight();
        }
    }

    private Long getNodeLevel(NestedSetDetail<ID> parent, Mode mode) {
        switch (mode) {
            case NEXT_SIBLING:
            case PREV_SIBLING:
                return parent.getLevel();
            case LAST_CHILD:
            case FIRST_CHILD:
            default:
                return parent.getLevel() + 1;
        }
    }

    private Optional<ID> getNodeParent(NestedSetDetail<ID> parent, Mode mode) {
        switch (mode) {
            case NEXT_SIBLING:
            case PREV_SIBLING:
                if (parent.getParentId() != null) {
                    return Optional.of(parent.getParentId());
                } else {
                    return Optional.empty();
                }
            case LAST_CHILD:
            case FIRST_CHILD:
            default:
                return Optional.of(parent.getId());
        }
    }

    private Long getNodeLeft(NestedSetDetail<ID> parent, Mode mode) {
        switch (mode) {
            case NEXT_SIBLING:
                return parent.getRight() + 1;
            case PREV_SIBLING:
                return parent.getLeft();
            case FIRST_CHILD:
                return parent.getLeft() + 1;
            case LAST_CHILD:
            default:
                return parent.getRight();
        }
    }

    private boolean applyGte(Mode mode) {
        switch (mode) {
            case NEXT_SIBLING:
            case FIRST_CHILD:
                return false;
            case PREV_SIBLING:
            case LAST_CHILD:
            default:
                return true;
        }
    }

}
