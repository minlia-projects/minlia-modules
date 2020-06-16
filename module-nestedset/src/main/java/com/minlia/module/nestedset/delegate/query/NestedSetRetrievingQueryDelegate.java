
package com.minlia.module.nestedset.delegate.query;

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

import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface NestedSetRetrievingQueryDelegate<ID extends Serializable, ENTITY extends NestedSet<ID>> {

    List<ENTITY> getTreeAsList(ENTITY entity);

    List<ENTITY> getChildren(ENTITY entity);

    Optional<ENTITY> getParent(ENTITY entity);

    List<ENTITY> getParents(ENTITY entity);

    Optional<ENTITY> getPrevSibling(ENTITY entity);

    Optional<ENTITY> getNextSibling(ENTITY entity);

    Optional<NestedSetDetail<ID>> getNestedSetDetail(ID nodeId);

    Optional<ENTITY> findFirstRoot();

    Optional<ENTITY> findLastRoot();
}
