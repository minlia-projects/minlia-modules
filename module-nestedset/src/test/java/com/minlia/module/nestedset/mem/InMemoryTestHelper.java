package com.minlia.module.nestedset.mem;

import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.model.TestSet;
import com.minlia.module.nestedset.TestConfiguration;
import com.minlia.module.nestedset.base.TestHelper;

import java.util.stream.Collectors;

public class InMemoryTestHelper implements TestHelper {

    private final InMemoryNestedNodeRepositoryConfiguration<Long, TestSet> config = TestConfiguration.IN_MEM_CONFIG;

    @Override
    public TestSet findNode(String symbol) {
        System.out.println(config.getNodes());
        return config.getNodes().stream().filter(n -> n.getName().equals(symbol)).findFirst().orElse(null);
    }

    @Override
    public TestSet getParent(TestSet f) {
        if (f.getParentId() == null) {
            return null;
        }
        TestSet parent = config.getNodes().stream().filter(n -> n.getId().equals(f.getParentId())).findFirst().orElse(null);
        System.out.println(String.format("Parent of %s is %s", f.getName(), parent != null ? parent.getName() : "null"));
        return parent;
    }

    @Override
    public void breakTree() {
        config.getNodes().stream()
                .filter(n -> n.getDiscriminator()
                        .equals("tree_1")).forEach(n -> {
            n.setLevel(0L);
            n.setLeft(0L);
            n.setRight(0L);
        });
    }

    @Override
    public void resetParent(String symbol) {
        findNode(symbol).setParentId(null);
    }

    @Override
    public void removeTree() {
        config.getNodes().stream().filter(n -> n.getDiscriminator().equals("tree_1"))
                .forEach(n -> config.getNodes().remove(n));
    }

    @Override
    public void save(TestSet node) {
        node.setId(config.getIdentityGenerator().generateIdentity());
        config.getNodes().add(node);
    }

    public void rollback() {
        config.getNodes().clear();
        config.getNodes().addAll(TestConfiguration.IN_MEM_NODES.stream().map(TestSet::copy).collect(Collectors.toList()));
    }
}
