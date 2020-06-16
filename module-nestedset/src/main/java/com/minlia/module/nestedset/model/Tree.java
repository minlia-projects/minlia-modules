package com.minlia.module.nestedset.model;

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

import java.io.Serializable;
import java.util.List;

/**
 * Recursive Data Structure used for Nested Tree branches retrieval
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public interface Tree<ID extends Serializable, N extends NestedSet<ID>> {
    
    void setChildren(List<Tree<ID, N>> children);
    
    void addChild(Tree<ID, N> child);
    
    List<Tree<ID, N>> getChildren();
    
    Tree<ID, N> getParent();
    
    void setParent(Tree<ID, N> parent);
    
    N getNode();
    
    void setNode(N node);
    
}
