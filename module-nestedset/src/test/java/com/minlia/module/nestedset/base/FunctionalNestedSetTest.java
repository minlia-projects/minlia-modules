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

import com.minlia.module.nestedset.DelegatingNestedNodeRepository;
import com.minlia.module.nestedset.model.TestSet;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.minlia.module.nestedset.TestConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class FunctionalNestedSetTest {

    /**
     * STARTING NESTED TREE CONDITIONS
     * <p/>
     *                  1 A 16
     *                   / \                    IDS:
     *                  /   \                   A: 1
     *                 /     \                  B: 2
     *              2 B 7   8 C 15              C: 3
     *               /         \                D: 4
     *              /\         /\               E: 5
     *             /  \       /  \              F: 6
     *            /    \     /    \             G: 7
     *           /   5 E 6  9 F 10 \            H: 8
     *        3 D 4             11 G 14
     *                              \
     *                               \
     *                             12 H 13
     */

    protected DelegatingNestedNodeRepository<Long, TestSet> repository;

    protected abstract TestSet findNode(String symbol);

    protected void printNode(String symbol, TestSet n) {
        if(n != null) {
            System.out.println(String.format("Node %s: %d/%d/%d", symbol, n.getLeft(), n.getRight(), n.getLevel()));
        }
    }

    protected TestSet createTestNode(String symbol) {

        TestSet n = new TestSet();
        n.setName(symbol);
        n.setDiscriminator("tree_1");
        return n;
    }

    protected abstract TestSet getParent(TestSet f);


    protected void assertSecondTreeIntact() {
        TestSet a2 = this.findNode("a2");
        TestSet b2 = this.findNode("b2");
        TestSet c2 = this.findNode("c2");
        TestSet d2 = this.findNode("d2");
        TestSet e2 = this.findNode("e2");
        TestSet g2 = this.findNode("g2");
        TestSet f2 = this.findNode("f2");
        TestSet h2 = this.findNode("h2");

        assertEquals(1, (long) a2.getLeft());
        assertEquals(16, (long) a2.getRight());
        assertEquals(2, (long) b2.getLeft());
        assertEquals(7, (long) b2.getRight());
        assertEquals(8, (long) c2.getLeft());
        assertEquals(15, (long) c2.getRight());
        assertEquals(3, (long) d2.getLeft());
        assertEquals(4, (long) d2.getRight());
        assertEquals(5, (long) e2.getLeft());
        assertEquals(6, (long) e2.getRight());
        assertEquals(9, (long) f2.getLeft());
        assertEquals(10, (long) f2.getRight());
        assertEquals(11, (long) g2.getLeft());
        assertEquals(14, (long) g2.getRight());
        assertEquals(12, (long) h2.getLeft());
        assertEquals(13, (long) h2.getRight());

        assertNull(this.getParent(a2));
        assertEquals(this.getParent(b2), a2);
        assertEquals(this.getParent(c2), a2);
        assertEquals(this.getParent(d2), b2);
        assertEquals(this.getParent(e2), b2);
        assertEquals(this.getParent(f2), c2);
        assertEquals(this.getParent(g2), c2);
        assertEquals(this.getParent(h2), g2);

    }

    protected abstract void breakTree();

    protected abstract void resetParent(String symbol);

    protected abstract void removeTree();

    protected void flushAndClear() {

    }

    protected void flush() {

    }

    protected void refresh(TestSet node) {

    }

    protected abstract void save(TestSet node);

}
