package com.minlia.module.nestedset.base;

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
