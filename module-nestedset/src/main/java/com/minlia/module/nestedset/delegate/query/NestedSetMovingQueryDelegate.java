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

package com.minlia.module.nestedset.delegate.query;

import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import java.io.Serializable;

public interface NestedSetMovingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>> {

    public Class<?> getClz();

    Integer markNodeIds(NestedSetDetail<ID> node);

    void updateSideFieldsUp(Long delta, Long start, Long stop, String field);

    void updateSideFieldsDown(Long delta, Long start, Long stop, String field);

    void performMoveUp(Long nodeDelta, Long levelModificator);

    void performMoveDown(Long nodeDelta, Long levelModificator);

    void updateParentField(ID newParentId, NestedSetDetail<ID> node);

    void clearParentField(NestedSetDetail<ID> node);
}
