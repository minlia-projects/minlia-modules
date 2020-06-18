package com.minlia.module.nestedset.base;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia Team
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
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
public abstract class NestedSetRepositoryTest extends FunctionalNestedSetTest {

    @Test
    public void testMultipleOperations() {

        TestSet i = this.createTestNode("i");
        TestSet j = this.createTestNode("j");
        TestSet k = this.createTestNode("k");
        TestSet l = this.createTestNode("l");
        TestSet m = this.createTestNode("m");
        TestSet a = this.findNode("a");

        this.repository.insertAsNextSiblingOf(i, a);
        flushAndClear();

        i = findNode("i");
        printNode("i", i);
        this.repository.insertAsLastChildOf(j, i);
        flushAndClear();

        i = findNode("i");
        j = findNode("j");
        printNode("i", i);
        printNode("j", j);
        this.repository.insertAsFirstChildOf(k, i);
       flushAndClear();

        k = findNode("k");
        this.repository.insertAsNextSiblingOf(l, k);
        flushAndClear();
        l = findNode("l");

        this.repository.insertAsPrevSiblingOf(m, l);
        flushAndClear();

        System.out.println("ASSERTS");
        a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet c = this.findNode("c");
        TestSet d = this.findNode("d");
        TestSet e = this.findNode("e");
        TestSet g = this.findNode("g");
        TestSet f = this.findNode("f");
        TestSet h = this.findNode("h");
        i = this.findNode("i");
        j = this.findNode("j");
        k = this.findNode("k");
        l = this.findNode("l");
        m = this.findNode("m");

        assertEquals(1, (long) a.getLeft());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) b.getLeft());
        assertEquals(7, (long) b.getRight());
        assertEquals(8, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(3, (long) d.getLeft());
        assertEquals(4, (long) d.getRight());
        assertEquals(5, (long) e.getLeft());
        assertEquals(6, (long) e.getRight());
        assertEquals(9, (long) f.getLeft());
        assertEquals(10, (long) f.getRight());
        assertEquals(11, (long) g.getLeft());
        assertEquals(14, (long) g.getRight());
        assertEquals(12, (long) h.getLeft());
        assertEquals(13, (long) h.getRight());

        assertEquals(17, (long) i.getLeft());
        assertEquals(26, (long) i.getRight());
        assertEquals(24, (long) j.getLeft());
        assertEquals(25, (long) j.getRight());
        assertEquals(18, (long) k.getLeft());
        assertEquals(19, (long) k.getRight());
        assertEquals(22, (long) l.getLeft());
        assertEquals(23, (long) l.getRight());
        assertEquals(20, (long) m.getLeft());
        assertEquals(21, (long) m.getRight());

        assertNull(this.getParent(i));
        assertEquals(this.getParent(j), i);
        assertEquals(this.getParent(k), i);
        assertSecondTreeIntact();

       breakTree();
        flushAndClear();

        repository.rebuildTree();
        flushAndClear();

        assertSecondTreeIntact();

        System.out.println("ASSERTS AFTER REBUILD");

        a = this.findNode("a");
        b = this.findNode("b");
        c = this.findNode("c");
        d = this.findNode("d");
        e = this.findNode("e");
        g = this.findNode("g");
        f = this.findNode("f");
        h = this.findNode("h");
        i = this.findNode("i");
        j = this.findNode("j");
        k = this.findNode("k");
        l = this.findNode("l");
        m = this.findNode("m");

        assertEquals(1, (long) a.getLeft());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) b.getLeft());
        assertEquals(7, (long) b.getRight());
        assertEquals(8, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(3, (long) d.getLeft());
        assertEquals(4, (long) d.getRight());
        assertEquals(5, (long) e.getLeft());
        assertEquals(6, (long) e.getRight());
        assertEquals(9, (long) f.getLeft());
        assertEquals(10, (long) f.getRight());
        assertEquals(11, (long) g.getLeft());
        assertEquals(14, (long) g.getRight());
        assertEquals(12, (long) h.getLeft());
        assertEquals(13, (long) h.getRight());

        assertEquals(17, (long) i.getLeft());
        assertEquals(26, (long) i.getRight());
        assertEquals(18, (long) j.getLeft());
        assertEquals(19, (long) j.getRight());
        assertEquals(20, (long) k.getLeft());
        assertEquals(21, (long) k.getRight());
        assertEquals(22, (long) l.getLeft());
        assertEquals(23, (long) l.getRight());
        assertEquals(24, (long) m.getLeft());
        assertEquals(25, (long) m.getRight());

        repository.removeSingle(i);

       flushAndClear();
        a = this.findNode("a");
        b = this.findNode("b");
        c = this.findNode("c");
        d = this.findNode("d");
        e = this.findNode("e");
        g = this.findNode("g");
        f = this.findNode("f");
        h = this.findNode("h");
        j = this.findNode("j");
        k = this.findNode("k");
        l = this.findNode("l");
        m = this.findNode("m");

        System.out.println("ASSERTS AFTER REMOVE SINGLE");

        assertEquals(1, (long) a.getLeft());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) b.getLeft());
        assertEquals(7, (long) b.getRight());
        assertEquals(8, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(3, (long) d.getLeft());
        assertEquals(4, (long) d.getRight());
        assertEquals(5, (long) e.getLeft());
        assertEquals(6, (long) e.getRight());
        assertEquals(9, (long) f.getLeft());
        assertEquals(10, (long) f.getRight());
        assertEquals(11, (long) g.getLeft());
        assertEquals(14, (long) g.getRight());
        assertEquals(12, (long) h.getLeft());
        assertEquals(13, (long) h.getRight());

        assertEquals(17, (long) j.getLeft());
        assertEquals(18, (long) j.getRight());
        assertEquals(0, (long) j.getLevel());
        assertNull(j.getParentId());
        assertEquals(19, (long) k.getLeft());
        assertEquals(20, (long) k.getRight());
        assertEquals(0, (long) k.getLevel());
        assertNull(k.getParentId());
        assertEquals(21, (long) l.getLeft());
        assertEquals(22, (long) l.getRight());
        assertEquals(0, (long) l.getLevel());
        assertNull(l.getParentId());
        assertEquals(23, (long) m.getLeft());
        assertEquals(24, (long) m.getRight());
        assertEquals(0, (long) m.getLevel());
        assertNull(m.getParentId());

        repository.removeSubtree(a);
        flushAndClear();

        j = this.findNode("j");
        k = this.findNode("k");
        l = this.findNode("l");
        m = this.findNode("m");

        System.out.println("ASSERTS AFTER REMOVE SUBTREE");

        assertEquals(1, (long) j.getLeft());
        assertEquals(2, (long) j.getRight());
        assertEquals(0, (long) j.getLevel());
        assertNull(j.getParentId());
        assertEquals(3, (long) k.getLeft());
        assertEquals(4, (long) k.getRight());
        assertEquals(0, (long) k.getLevel());
        assertNull(k.getParentId());
        assertEquals(5, (long) l.getLeft());
        assertEquals(6, (long) l.getRight());
        assertEquals(0, (long) l.getLevel());
        assertNull(l.getParentId());
        assertEquals(7, (long) m.getLeft());
        assertEquals(8, (long) m.getRight());
        assertEquals(0, (long) m.getLevel());
        assertNull(m.getParentId());
    }
}
