
package com.minlia.module.nestedset.model;

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

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Internal representation of Nested Node, not intended for use outside of our implementation.
 *
 * @param <ID> - Nested Node Identifier Class
 */
public class NestedSetDetail<ID extends Serializable> {

    private final ID id;

    private final ID parentId;

    private final Long left;

    private final Long right;

    private final Long level;

    public NestedSetDetail(ID id, ID parentId, Long left, Long right, Long level) {
        this.id = id;
        this.parentId = parentId;
        this.left = left;
        this.right = right;
        this.level = level;
    }

    public ID getId() {
        return id;
    }

    public ID getParentId() {
        return parentId;
    }

    public Long getLeft() {
        return left;
    }

    public Long getRight() {
        return right;
    }

    public Long getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("parentId", parentId)
                .add("left", left)
                .add("right", right)
                .add("level", level)
                .toString();
    }
}
