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
package com.minlia.module.nestedset.delegate.control;

import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.config.ConfigurationUtils;
import com.minlia.module.nestedset.delegate.NestedSetCreator;
import com.minlia.module.nestedset.delegate.query.NestedSetInsertingQueryDelegate;

import java.io.Serializable;
import java.util.Optional;

public class QueryBasedNestedSetCreator<ID extends Serializable, N extends NestedSet<ID>> implements NestedSetCreator<ID, N> {

    private final NestedSetInsertingQueryDelegate<ID, N> queryDelegate;

    public QueryBasedNestedSetCreator(NestedSetInsertingQueryDelegate<ID, N> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void create(N node, NestedSetDetail<ID> parentInfo, Mode mode) {
        makeSpaceForNewElement(getMoveFrom(parentInfo, mode), mode);
        insertNodeIntoTree(parentInfo, node, mode);
    }

    @Override
    public void createAsFirstNode(N node) {
        node.setLeft(1L);
        node.setRight(2L);
        node.setLevel(0L);
        node.setParentId(null);
        queryDelegate.insert(node);
    }

    private void insertNodeIntoTree(NestedSetDetail<ID> parent, N node, Mode mode) {
        Long left = this.getNodeLeft(parent, mode);
        Long right = left + 1;
        Long level = this.getNodeLevel(parent, mode);
        node.setLeft(left);
        node.setRight(right);
        node.setLevel(level);
        node.setParentId(this.getNodeParent(parent, mode).orElse(null));
        queryDelegate.insert(node);
    }

    private void makeSpaceForNewElement(Long from, Mode mode) {

//        ResolvableType resolvableType = ResolvableType.forClass(getClass());
////        ResolvableType resolvableType = ResolvableType.forClass(this.getClass());
////        ResolvableType x=resolvableType.getGeneric(2);
////        ResolvableType resolvableType = ResolvableType.forClass(getClass());
////        Class<?> entityClass = (Class<?>) resolvableType.getType().getClass();
//        Class<?> clazz = resolvableType.getGeneric(1).getSuperType().resolve();
//        System.out.println(clazz);
//
//        Class<?> clz =resolvableType.getGenerics()[1].resolve();
//        System.out.println(resolvableType.getGenerics()[1].resolve());
//        System.out.println(((ParameterizedType) x.getType()).getActualTypeArguments());

//        Class<?> clz = AnnotationUtils.getType(this, 1);
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
