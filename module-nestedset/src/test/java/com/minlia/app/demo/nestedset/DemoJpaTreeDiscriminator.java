package com.minlia.app.demo.nestedset;

import com.minlia.app.demo.nestedset.entity.Team;
import com.minlia.module.nestedset.config.jpa.discriminator.MapJpaTreeDiscriminator;

import java.util.Collections;

public class DemoJpaTreeDiscriminator extends MapJpaTreeDiscriminator<Long, Team> {

    public DemoJpaTreeDiscriminator() {
        super(Collections.singletonMap("discriminator", () -> "tree_1"));
    }
}
