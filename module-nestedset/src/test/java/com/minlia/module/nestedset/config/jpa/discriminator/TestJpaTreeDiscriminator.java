package com.minlia.module.nestedset.config.jpa.discriminator;

import com.minlia.module.nestedset.model.TestSet;

import java.util.Collections;

public class TestJpaTreeDiscriminator extends MapJpaTreeDiscriminator<Long, TestSet> {

    public TestJpaTreeDiscriminator() {
        super(Collections.singletonMap("discriminator", () -> "tree_1"));
    }
}
