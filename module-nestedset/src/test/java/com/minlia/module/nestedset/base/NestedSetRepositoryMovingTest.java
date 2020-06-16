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
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
public abstract class NestedSetRepositoryMovingTest extends FunctionalNestedSetTest {

    @Test
    public void testInsertAsLastChildOfDeepMove() {
        TestSet b = this.findNode("b");
        TestSet a = this.findNode("a");
        this.repository.insertAsLastChildOf(b, a);
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");

        refresh(b);
        refresh(a);
        assertEquals(2, (long) c.getLeft());
        assertEquals(9, (long) c.getRight());
        assertEquals(10, (long) b.getLeft());
        assertEquals(15, (long) b.getRight());
        assertEquals(5, (long) g.getLeft());
        assertEquals(8, (long) g.getRight());
        assertEquals(11, (long) d.getLeft());
        assertEquals(12, (long) d.getRight());
        assertEquals(1, (long) b.getLevel());
        assertEquals(2, (long) d.getLevel());
        assertEquals(this.getParent(b), a);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstChildOfDeepMove() {
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        this.repository.insertAsFirstChildOf(c, a);
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet b = this.findNode("b");

        refresh(c);
        refresh(a);
        assertEquals(2, (long) c.getLeft());
        assertEquals(9, (long) c.getRight());
        assertEquals(10, (long) b.getLeft());
        assertEquals(15, (long) b.getRight());
        assertEquals(5, (long) g.getLeft());
        assertEquals(8, (long) g.getRight());
        assertEquals(11, (long) d.getLeft());
        assertEquals(12, (long) d.getRight());
        assertEquals(2, (long) g.getLevel());
        assertEquals(1, (long) c.getLevel());
        assertEquals(this.getParent(c), a);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsNextSiblingOfDeepMove() {
        TestSet b = this.findNode("b");
        TestSet a = this.findNode("a");
        this.repository.insertAsNextSiblingOf(b, a);
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet e = this.findNode("e");

        refresh(b);
        refresh(a);
        assertEquals(11, (long) b.getLeft());
        assertEquals(16, (long) b.getRight());
        assertEquals(1, (long) a.getLeft());
        assertEquals(10, (long) a.getRight());
        assertEquals(5, (long) g.getLeft());
        assertEquals(8, (long) g.getRight());
        assertEquals(12, (long) d.getLeft());
        assertEquals(13, (long) d.getRight());
        assertEquals(0, (long) b.getLevel());
        assertEquals(1, (long) d.getLevel());
        assertEquals(1, (long) e.getLevel());
        assertNull(this.getParent(b));
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsPrevSiblingOfDeepMove() {
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        this.repository.insertAsPrevSiblingOf(c, a);
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet f = this.findNode("f");
        TestSet h = this.findNode("h");

        refresh(c);
        refresh(a);
        assertEquals(1, (long) c.getLeft());
        assertEquals(8, (long) c.getRight());
        assertEquals(9, (long) a.getLeft());
        assertEquals(16, (long) a.getRight());
        assertEquals(4, (long) g.getLeft());
        assertEquals(7, (long) g.getRight());
        assertEquals(11, (long) d.getLeft());
        assertEquals(12, (long) d.getRight());
        assertEquals(0, (long) c.getLevel());
        assertEquals(1, (long) f.getLevel());
        assertEquals(1, (long) g.getLevel());
        assertEquals(2, (long) h.getLevel());
        assertNull(this.getParent(c));
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsPrevSiblingOfMoveRight() {
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        this.repository.insertAsPrevSiblingOf(d, g);
        TestSet f = this.findNode("f");
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet e = this.findNode("e");
        TestSet h = this.findNode("h");

        refresh(d);
        refresh(g);
        assertEquals(3, (long) e.getLeft());
        assertEquals(4, (long) e.getRight());
        assertEquals(5, (long) b.getRight());
        assertEquals(7, (long) f.getLeft());
        assertEquals(8, (long) f.getRight());
        assertEquals(11, (long) g.getLeft());
        assertEquals(14, (long) g.getRight());
        assertEquals(9, (long) d.getLeft());
        assertEquals(10, (long) d.getRight());
        assertEquals(12, (long) h.getLeft());
        assertEquals(13, (long) h.getRight());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) d.getLevel());

        assertEquals(this.getParent(d), c);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsPrevSiblingOfMoveLeft() {
        TestSet g = this.findNode("g");
        TestSet e = this.findNode("e");
        this.repository.insertAsPrevSiblingOf(g, e);
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet h = this.findNode("h");

        refresh(g);
        refresh(e);
        assertEquals(5, (long) g.getLeft());
        assertEquals(8, (long) g.getRight());
        assertEquals(6, (long) h.getLeft());
        assertEquals(7, (long) h.getRight());
        assertEquals(9, (long) e.getLeft());
        assertEquals(10, (long) e.getRight());
        assertEquals(11, (long) b.getRight());
        assertEquals(12, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) g.getLevel());
        assertEquals(3, (long) h.getLevel());
        assertEquals(this.getParent(g), b);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsNextSiblingOfMoveRight() {
        TestSet d = this.findNode("d");
        TestSet f = this.findNode("f");
        this.repository.insertAsNextSiblingOf(d, f);
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet e = this.findNode("e");
        TestSet h = this.findNode("h");

        refresh(d);
        refresh(f);
        assertEquals(3, (long) e.getLeft());
        assertEquals(4, (long) e.getRight());
        assertEquals(5, (long) b.getRight());
        assertEquals(7, (long) f.getLeft());
        assertEquals(8, (long) f.getRight());
        assertEquals(11, (long) g.getLeft());
        assertEquals(14, (long) g.getRight());
        assertEquals(9, (long) d.getLeft());
        assertEquals(10, (long) d.getRight());
        assertEquals(12, (long) h.getLeft());
        assertEquals(13, (long) h.getRight());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) d.getLevel());
        assertEquals(this.getParent(d), c);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsNextSiblingOfMoveLeft() {
        TestSet g = this.findNode("g");
        TestSet d = this.findNode("d");
        this.repository.insertAsNextSiblingOf(g, d);
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet e = this.findNode("e");
        TestSet h = this.findNode("h");

        refresh(d);
        refresh(g);
        assertEquals(5, (long) g.getLeft());
        assertEquals(8, (long) g.getRight());
        assertEquals(6, (long) h.getLeft());
        assertEquals(7, (long) h.getRight());
        assertEquals(9, (long) e.getLeft());
        assertEquals(10, (long) e.getRight());
        assertEquals(11, (long) b.getRight());
        assertEquals(12, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) g.getLevel());
        assertEquals(3, (long) h.getLevel());
        assertEquals(this.getParent(g), b);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsLastChildOfMoveLeft() {
        TestSet g = this.findNode("g");
        TestSet b = this.findNode("b");
        this.repository.insertAsLastChildOf(g, b);
        TestSet f = this.findNode("f");
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet d = this.findNode("d");
        TestSet h = this.findNode("h");

        refresh(g);
        refresh(b);
        assertEquals(7, (long) g.getLeft());
        assertEquals(10, (long) g.getRight());
        assertEquals(8, (long) h.getLeft());
        assertEquals(9, (long) h.getRight());
        assertEquals(11, (long) b.getRight());
        assertEquals(13, (long) f.getLeft());
        assertEquals(14, (long) f.getRight());
        assertEquals(12, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) g.getLevel());
        assertEquals(3, (long) h.getLevel());
        assertEquals(this.getParent(g), b);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsLastChildOfMoveRight() {
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        this.repository.insertAsLastChildOf(d, g);
        TestSet f = this.findNode("f");
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet e = this.findNode("e");
        TestSet h = this.findNode("h");

        refresh(d);
        refresh(g);
        assertEquals(3, (long) e.getLeft());
        assertEquals(4, (long) e.getRight());
        assertEquals(5, (long) b.getRight());
        assertEquals(7, (long) f.getLeft());
        assertEquals(9, (long) g.getLeft());
        assertEquals(12, (long) d.getLeft());
        assertEquals(13, (long) d.getRight());
        assertEquals(10, (long) h.getLeft());
        assertEquals(11, (long) h.getRight());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(3, (long) d.getLevel());
        assertEquals(this.getParent(d), g);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstChildOfMoveRight() {
        TestSet d = findNode("d");
        TestSet g = findNode("g");
        this.repository.insertAsFirstChildOf(d, g);

        refresh(d);
        refresh(g);
        TestSet f = findNode("f");
        TestSet c = findNode("c");
        TestSet a = findNode("a");
        TestSet b = findNode("b");
        TestSet e = findNode("e");
        TestSet h = findNode("h");

        assertEquals(3, (long) e.getLeft());
        assertEquals(4, (long) e.getRight());
        assertEquals(5, (long) b.getRight());
        assertEquals(7, (long) f.getLeft());
        assertEquals(9, (long) g.getLeft());
        assertEquals(10, (long) d.getLeft());
        assertEquals(11, (long) d.getRight());
        assertEquals(12, (long) h.getLeft());
        assertEquals(13, (long) h.getRight());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(3, (long) d.getLevel());
        assertEquals(this.getParent(d), g);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstChildOfMoveLeft() {
        TestSet g = this.findNode("g");
        TestSet b = this.findNode("b");
        this.repository.insertAsFirstChildOf(g, b);
        TestSet f = this.findNode("f");
        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        TestSet h = this.findNode("h");

        refresh(g);
        refresh(b);
        assertEquals(3, (long) g.getLeft());
        assertEquals(6, (long) g.getRight());
        assertEquals(13, (long) f.getLeft());
        assertEquals(14, (long) f.getRight());
        assertEquals(12, (long) c.getLeft());
        assertEquals(15, (long) c.getRight());
        assertEquals(16, (long) a.getRight());
        assertEquals(2, (long) g.getLevel());
        assertEquals(3, (long) h.getLevel());
        assertEquals(this.getParent(g), b);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsNextSiblingOfMoveEdge() {
        TestSet h = this.findNode("h");
        TestSet c = this.findNode("c");
        this.repository.insertAsLastChildOf(h, c);

        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        c = this.findNode("c");
        TestSet d = this.findNode("d");
        TestSet e = this.findNode("e");
        TestSet g = this.findNode("g");
        TestSet f = this.findNode("f");
        h = this.findNode("h");

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
        assertEquals(12, (long) g.getRight());
        assertEquals(13, (long) h.getLeft());
        assertEquals(14, (long) h.getRight());
        assertEquals(this.getParent(h), c);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsNextSiblingOfMoveSecondRoot() {

        TestSet c = this.findNode("c");
        TestSet a = this.findNode("a");
        this.repository.insertAsNextSiblingOf(c, a);

        a = this.findNode("a");
        TestSet b = this.findNode("b");
        c = this.findNode("c");
        TestSet d = this.findNode("d");
        TestSet e = this.findNode("e");
        TestSet g = this.findNode("g");
        TestSet f = this.findNode("f");
        TestSet h = this.findNode("h");

        assertEquals(1, (long) a.getLeft());
        assertEquals(8, (long) a.getRight());
        assertEquals(2, (long) b.getLeft());
        assertEquals(7, (long) b.getRight());
        assertEquals(9, (long) c.getLeft());
        assertEquals(16, (long) c.getRight());
        assertEquals(3, (long) d.getLeft());
        assertEquals(4, (long) d.getRight());
        assertEquals(5, (long) e.getLeft());
        assertEquals(6, (long) e.getRight());
        assertEquals(10, (long) f.getLeft());
        assertEquals(11, (long) f.getRight());
        assertEquals(12, (long) g.getLeft());
        assertEquals(15, (long) g.getRight());
        assertEquals(13, (long) h.getLeft());
        assertEquals(14, (long) h.getRight());
        assertNull(this.getParent(c));
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertFirstRootAsFirstRoot() {
        TestSet x = this.createTestNode("x");
        this.repository.insertAsFirstRoot(x);
        flushAndClear();
        this.repository.insertAsFirstRoot(x);
        flushAndClear();
        x = findNode("x");
        TestSet a = findNode("a");
        assertEquals(1, (long) x.getLeft());
        assertEquals(2, (long) x.getRight());
        assertEquals(0, (long) x.getLevel());
        assertNull(x.getParentId());

        assertEquals(3, (long) a.getLeft());
        assertEquals(18, (long) a.getRight());
        assertEquals(0, (long) a.getLevel());
        assertNull(x.getParentId());
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertLastRootAsLastRoot() {
        TestSet x = this.createTestNode("x");
        this.repository.insertAsLastRoot(x);
        flushAndClear();
        this.repository.insertAsLastRoot(x);
        flushAndClear();
        x = findNode("x");
        TestSet a = findNode("a");
        assertEquals(17, (long) x.getLeft());
        assertEquals(18, (long) x.getRight());
        assertEquals(0, (long) x.getLevel());
        assertNull(x.getParentId());

        assertEquals(1, (long) a.getLeft());
        assertEquals(16, (long) a.getRight());
        assertEquals(0, (long) a.getLevel());
        assertNull(x.getParentId());
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertLastRootAsFirstRoot() {
        TestSet x = this.createTestNode("x");
        this.repository.insertAsLastRoot(x);
        flushAndClear();
        this.repository.insertAsFirstRoot(x);
        flushAndClear();
        x = findNode("x");
        TestSet a = findNode("a");
        assertEquals(1, (long) x.getLeft());
        assertEquals(2, (long) x.getRight());
        assertEquals(0, (long) x.getLevel());
        assertNull(x.getParentId());

        assertEquals(3, (long) a.getLeft());
        assertEquals(18, (long) a.getRight());
        assertEquals(0, (long) a.getLevel());
        assertNull(x.getParentId());
        assertSecondTreeIntact();
    }

    @Test
    public void testInserFirstRootAsLastRoot() {
        TestSet x = this.createTestNode("x");
        this.repository.insertAsFirstRoot(x);
        flushAndClear();
        this.repository.insertAsLastRoot(x);
        flushAndClear();
        x = findNode("x");
        TestSet a = findNode("a");
        assertEquals(17, (long) x.getLeft());
        assertEquals(18, (long) x.getRight());
        assertEquals(0, (long) x.getLevel());
        assertNull(x.getParentId());

        assertEquals(1, (long) a.getLeft());
        assertEquals(16, (long) a.getRight());
        assertEquals(0, (long) a.getLevel());
        assertNull(x.getParentId());
        assertSecondTreeIntact();
    }

}
