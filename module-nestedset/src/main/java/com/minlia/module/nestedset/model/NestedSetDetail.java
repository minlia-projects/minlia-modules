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

package com.minlia.module.nestedset.model;

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
