package com.minlia.module.nestedset.base;

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

import com.minlia.module.nestedset.model.TestSet;

public interface TestHelper {

    TestSet findNode(String symbol);

    TestSet getParent(TestSet f);

    void breakTree();

    void resetParent(String symbol);

    void removeTree();

    static void printNode(String symbol, TestSet n) {
        if(n != null) {
            System.out.println(String.format("Node %s: %d/%d/%d", symbol, n.getLeft(), n.getRight(), n.getLevel()));
        }
    }

    default void flushAndClear() {

    }

    default void flush() {

    }

    default void refresh(TestSet node) {

    }

   void save(TestSet node);
}
