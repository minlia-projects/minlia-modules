/*
 * The MIT License
 *
 * Copyright 2015 exsio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.minlia.module.nestedset.base;

import com.minlia.module.nestedset.exception.InvalidNodesHierarchyException;
import com.minlia.module.nestedset.model.TestSet;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
public abstract class NestedSetRepositoryInsertingTest extends FunctionalNestedSetTest {

    @Test(expected = InvalidNodesHierarchyException.class)
    public void testInsertParentToChildAsSibling() {
        TestSet a = this.findNode("a");
        TestSet e = this.findNode("e");
        this.repository.insertAsNextSiblingOf(a, e);
        assertSecondTreeIntact();
    }

    @Test(expected = InvalidNodesHierarchyException.class)
    public void testInsertParentToChildAsChild() {
        TestSet a = this.findNode("a");
        TestSet e = this.findNode("e");
        this.repository.insertAsLastChildOf(a, e);
        assertSecondTreeIntact();
    }

    @Test(expected = InvalidNodesHierarchyException.class)
    public void testInsertAsNextSiblingSameNode() {
        TestSet a = this.findNode("a");

        this.repository.insertAsNextSiblingOf(a, a);

        assertSecondTreeIntact();
    }

    @Test(expected = InvalidNodesHierarchyException.class)
    public void testInsertAsLastChildSameNode() {
        TestSet b = this.findNode("b");

        this.repository.insertAsLastChildOf(b, b);
        assertSecondTreeIntact();
    }

    @Test(expected = InvalidNodesHierarchyException.class)
    public void testInsertAsPrevSiblingSameNode() {
        TestSet c = this.findNode("c");

        this.repository.insertAsPrevSiblingOf(c, c);
        assertSecondTreeIntact();
    }

    @Test(expected = InvalidNodesHierarchyException.class)
    public void testInsertAsFirstChildSameNode() {
        TestSet d = this.findNode("d");

        this.repository.insertAsFirstChildOf(d, d);

        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstChildOfInsert() {

        TestSet i = this.createTestNode("i");
        TestSet e = this.findNode("e");
        this.repository.insertAsFirstChildOf(i, e);
        TestSet a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet h = this.findNode("h");

        assertEquals(6, (long) i.getLeft());
        assertEquals(7, (long) i.getRight());
        assertEquals(18, (long) a.getRight());
        assertEquals(9, (long) b.getRight());
        assertEquals(14, (long) h.getLeft());
        assertEquals(15, (long) h.getRight());
        assertEquals((long) i.getLevel(), e.getLevel() + 1);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstChildOfInsertNextToSibling() {

        TestSet i = this.createTestNode("i");
        TestSet b = this.findNode("b");
        this.repository.insertAsFirstChildOf(i, b);
        TestSet a = this.findNode("a");
        flush();
        refresh(i);
        refresh(b);
        printNode("i", i);
        b = findNode("b");
        TestSet h = this.findNode("h");
        TestSet d = this.findNode("d");
        TestSet e = this.findNode("e");

        assertEquals(3, (long) i.getLeft());
        assertEquals(4, (long) i.getRight());
        assertEquals(18, (long) a.getRight());
        assertEquals(2, (long) b.getLeft());
        assertEquals(9, (long) b.getRight());
        assertEquals(5, (long) d.getLeft());
        assertEquals(6, (long) d.getRight());
        assertEquals(7, (long) e.getLeft());
        assertEquals(8, (long) e.getRight());
        assertEquals(14, (long) h.getLeft());
        assertEquals(15, (long) h.getRight());
        assertEquals((long) i.getLevel(), b.getLevel() + 1);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsLastChildOfInsert() {

        TestSet j = this.createTestNode("j");
        TestSet b = this.findNode("b");
        this.repository.insertAsLastChildOf(j, b);
        TestSet a = this.findNode("a");
        TestSet h = this.findNode("h");
        TestSet c = this.findNode("c");

        assertEquals(7, (long) j.getLeft());
        assertEquals(8, (long) j.getRight());
        assertEquals(18, (long) a.getRight());
        assertEquals(14, (long) h.getLeft());
        assertEquals(15, (long) h.getRight());
        assertEquals(10, (long) c.getLeft());
        assertEquals((long) j.getLevel(), b.getLevel() + 1);
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsPrevSiblingOfInsert() {

        TestSet k = this.createTestNode("k");
        TestSet e = this.findNode("e");
        this.repository.insertAsPrevSiblingOf(k, e);
        flushAndClear();
        TestSet a = this.findNode("a");
        TestSet h = this.findNode("h");
        TestSet c = this.findNode("c");

        assertEquals(5, (long) k.getLeft());
        assertEquals(6, (long) k.getRight());
        assertEquals(18, (long) a.getRight());
        assertEquals(14, (long) h.getLeft());
        assertEquals(15, (long) h.getRight());
        assertEquals(10, (long) c.getLeft());
        assertEquals(k.getLevel(), e.getLevel());
        assertEquals(k.getParentId(), e.getParentId());
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsNextSiblingOfInsert() {

        TestSet m = this.createTestNode("m");
        TestSet h = this.findNode("h");
        this.repository.insertAsNextSiblingOf(m, h);
        TestSet a = this.findNode("a");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");

        assertEquals(14, (long) m.getLeft());
        assertEquals(15, (long) m.getRight());
        assertEquals(18, (long) a.getRight());
        assertEquals(16, (long) g.getRight());
        assertEquals(17, (long) c.getRight());
        assertEquals(m.getLevel(), h.getLevel());
        assertEquals(m.getParentId(), h.getParentId());
        assertSecondTreeIntact();
    }


    @Test
    public void testInsertAsFirstNodeFirstRoot() {
        this.removeTree();
        TestSet x = this.createTestNode("x");

        this.repository.insertAsFirstRoot(x);
        flushAndClear();
        x = findNode("x");
        assertEquals(1, (long) x.getLeft());
        assertEquals(2, (long) x.getRight());
        assertEquals(0, (long) x.getLevel());
        assertNull(x.getParentId());
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstNodeLastRoot() {
        this.removeTree();
        TestSet x = this.createTestNode("x");

        this.repository.insertAsLastRoot(x);
        flushAndClear();
        x = findNode("x");
        assertEquals(1, (long) x.getLeft());
        assertEquals(2, (long) x.getRight());
        assertEquals(0, (long) x.getLevel());
        assertNull(x.getParentId());
        assertSecondTreeIntact();
    }

    @Test
    public void testInsertAsFirstRoot() {
        TestSet x = this.createTestNode("x");
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
    public void testInsertAsLastRoot() {
        TestSet x = this.createTestNode("x");
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
