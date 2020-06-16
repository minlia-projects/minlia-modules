package com.minlia.module.nestedset.mem;

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
