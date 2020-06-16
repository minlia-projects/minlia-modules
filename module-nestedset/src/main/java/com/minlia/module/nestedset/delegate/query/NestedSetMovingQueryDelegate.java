
package com.minlia.module.nestedset.delegate.query;

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

import java.io.Serializable;

public interface NestedSetMovingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>> {

    public Class<?> getClz();

    Integer markNodeIds(NestedSetDetail<ID> nestedSetDetail);

    void updateSideFieldsUp(Long delta, Long start, Long stop, String field);

    void updateSideFieldsDown(Long delta, Long start, Long stop, String field);

    void performMoveUp(Long nodeDelta, Long levelModificator);

    void performMoveDown(Long nodeDelta, Long levelModificator);

    void updateParentField(ID newParentId, NestedSetDetail<ID> nestedSetDetail);

    void clearParentField(NestedSetDetail<ID> nestedSetDetail);
}
