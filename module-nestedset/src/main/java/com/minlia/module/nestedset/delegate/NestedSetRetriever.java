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
package com.minlia.module.nestedset.delegate;

import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.model.Tree;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface NestedSetRetriever<ID extends Serializable, N extends NestedSet<ID>> {

    List<N> getTreeAsList(N node);

    List<N> getChildren(N node);

    Optional<N> getParent(N nodes);

    Optional<N> getPrevSibling(N node);

    Optional<N> getNextSibling(N node);

    Tree<ID, N> getTree(N node);

    List<N> getParents(N node);

    Optional<NestedSetDetail<ID>> getNodeInfo(ID nodeIds);

    Optional<N> findFirstRoot();

    Optional<N> findLastRoot();
}
