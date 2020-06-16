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

import com.minlia.module.nestedset.exception.InvalidNodesHierarchyException;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.config.ConfigurationUtils;
import com.minlia.module.nestedset.delegate.NestedSetMover;
import com.minlia.module.nestedset.delegate.query.NestedSetMovingQueryDelegate;

import java.io.Serializable;
import java.util.Optional;

public class QueryBasedNestedSetMover<ID extends Serializable, ENTITY extends NestedSet<ID>> implements NestedSetMover<ID, ENTITY> {

    private final static long DELTA_MULTIPLIER = 2L;

    private enum Sign {
        PLUS, MINUS
    }

    private final NestedSetMovingQueryDelegate<ID, ENTITY> queryDelegate;

    public QueryBasedNestedSetMover(NestedSetMovingQueryDelegate<ID, ENTITY> queryDelegate) {
        this.queryDelegate = queryDelegate;
    }

    @Override
    public void move(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail, Mode mode) {
        if (!canMoveNodeToSelectedParent(nestedSetDetail, parentNestedSetDetail)) {
            throw new InvalidNodesHierarchyException("You cannot move a parent node to it's child or move a node to itself");
        }
        Integer nodeCount = queryDelegate.markNodeIds(nestedSetDetail);

        Sign sign = getSign(nestedSetDetail, parentNestedSetDetail, mode);
        Long start = getStart(nestedSetDetail, parentNestedSetDetail, mode, sign);
        Long stop = getStop(nestedSetDetail, parentNestedSetDetail, mode, sign);
        Long delta = getDelta(nodeCount);
        makeSpaceForMovedElement(sign, delta, start, stop);

        Long nodeDelta = getNodeDelta(start, stop);
        Sign nodeSign = getNodeSign(sign);
        Long levelModificator = getLevelModificator(nestedSetDetail, parentNestedSetDetail, mode);
        performMove(nodeDelta, nodeSign, levelModificator);
        updateParent(nestedSetDetail, parentNestedSetDetail, mode);
    }

    private void updateParent(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail, Mode mode) {
        Optional<ID> newParent = getNewParentId(parentNestedSetDetail, mode);
        if (newParent.isPresent()) {
            queryDelegate.updateParentField(newParent.get(), nestedSetDetail);
        } else {
            queryDelegate.clearParentField(nestedSetDetail);
        }
    }

    private void performMove(Long nodeDelta, Sign nodeSign, Long levelModificator) {
        if(Sign.PLUS.equals(nodeSign)) {
            queryDelegate.performMoveUp(nodeDelta, levelModificator);
        } else if(Sign.MINUS.equals(nodeSign)) {
            queryDelegate.performMoveDown(nodeDelta, levelModificator);
        }
    }

    private void makeSpaceForMovedElement(Sign sign, Long delta, Long start, Long stop) {
        if(Sign.PLUS.equals(sign)) {
            queryDelegate.updateSideFieldsUp(delta, start, stop, ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
            queryDelegate.updateSideFieldsUp(delta, start, stop,  ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
        } else if(Sign.MINUS.equals(sign)) {
            queryDelegate.updateSideFieldsDown(delta, start, stop, ConfigurationUtils.getRightFieldName(queryDelegate.getClz()));
            queryDelegate.updateSideFieldsDown(delta, start, stop, ConfigurationUtils.getLeftFieldName(queryDelegate.getClz()));
        }
    }

    private boolean canMoveNodeToSelectedParent(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail) {
        return !nestedSetDetail.getId().equals(parentNestedSetDetail.getId()) && (nestedSetDetail.getLeft() >= parentNestedSetDetail.getLeft() || nestedSetDetail.getRight() <= parentNestedSetDetail.getRight());
    }

    private Optional<ID> getNewParentId(NestedSetDetail<ID> parentNestedSetDetail, Mode mode) {
        switch (mode) {
            case NEXT_SIBLING:
            case PREV_SIBLING:
                if (parentNestedSetDetail.getParentId() != null) {
                    return Optional.of(parentNestedSetDetail.getParentId());
                } else {
                    return Optional.empty();
                }
            case FIRST_CHILD:
            case LAST_CHILD:
            default:
                return Optional.of(parentNestedSetDetail.getId());
        }
    }

    private Long getLevelModificator(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail, Mode mode) {
        switch (mode) {
            case NEXT_SIBLING:
            case PREV_SIBLING:
                return parentNestedSetDetail.getLevel() - nestedSetDetail.getLevel();
            case FIRST_CHILD:
            case LAST_CHILD:
            default:
                return parentNestedSetDetail.getLevel() + 1 - nestedSetDetail.getLevel();
        }
    }

    private Long getNodeDelta(Long start, Long stop) {
        return stop - start - 1;
    }

    private Long getDelta(Integer nodeCount) {
        return nodeCount * DELTA_MULTIPLIER;
    }

    private Sign getNodeSign(Sign sign) {
        return (sign.equals(Sign.PLUS)) ? Sign.MINUS : Sign.PLUS;
    }

    private Sign getSign(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail, Mode mode) {
        switch (mode) {
            case PREV_SIBLING:
            case FIRST_CHILD:
                return (nestedSetDetail.getRight() - parentNestedSetDetail.getLeft()) > 0 ? Sign.PLUS : Sign.MINUS;
            case NEXT_SIBLING:
            case LAST_CHILD:
            default:
                return (nestedSetDetail.getLeft() - parentNestedSetDetail.getRight()) > 0 ? Sign.PLUS : Sign.MINUS;
        }
    }

    private Long getStart(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail, Mode mode, Sign sign) {
        switch (mode) {
            case PREV_SIBLING:
                return sign.equals(Sign.PLUS) ? parentNestedSetDetail.getLeft() - 1 : nestedSetDetail.getRight();
            case FIRST_CHILD:
                return sign.equals(Sign.PLUS) ? parentNestedSetDetail.getLeft() : nestedSetDetail.getRight();
            case NEXT_SIBLING:
                return sign.equals(Sign.PLUS) ? parentNestedSetDetail.getRight() : nestedSetDetail.getRight();
            case LAST_CHILD:
            default:
                return sign.equals(Sign.PLUS) ? parentNestedSetDetail.getRight() - 1 : nestedSetDetail.getRight();

        }
    }

    private Long getStop(NestedSetDetail<ID> nestedSetDetail, NestedSetDetail<ID> parentNestedSetDetail, Mode mode, Sign sign) {
        switch (mode) {
            case PREV_SIBLING:
                return sign.equals(Sign.PLUS) ? nestedSetDetail.getLeft() : parentNestedSetDetail.getLeft();
            case FIRST_CHILD:
                return sign.equals(Sign.PLUS) ? nestedSetDetail.getLeft() : parentNestedSetDetail.getLeft() + 1;
            case NEXT_SIBLING:
                return sign.equals(Sign.PLUS) ? nestedSetDetail.getLeft() : parentNestedSetDetail.getRight() + 1;
            case LAST_CHILD:
            default:
                return sign.equals(Sign.PLUS) ? nestedSetDetail.getLeft() : parentNestedSetDetail.getRight();
        }
    }

}
