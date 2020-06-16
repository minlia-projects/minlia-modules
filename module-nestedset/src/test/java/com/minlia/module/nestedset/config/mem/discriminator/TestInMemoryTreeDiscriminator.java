package com.minlia.module.nestedset.config.mem.discriminator;

import com.minlia.module.nestedset.model.TestSet;

public class TestInMemoryTreeDiscriminator implements InMemoryTreeDiscriminator<Long, TestSet> {
    @Override
    public boolean applies(TestSet node) {
        return "tree_1".equals(node.getDiscriminator());
    }
}
