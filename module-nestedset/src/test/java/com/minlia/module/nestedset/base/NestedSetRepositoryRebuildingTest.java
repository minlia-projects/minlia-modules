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

import com.minlia.module.nestedset.model.TestSet;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
public abstract class NestedSetRepositoryRebuildingTest extends FunctionalNestedSetTest {

    @Test
    public void testInitializeTree() {
        this.removeTree();
        TestSet x = this.createTestNode("x");
        x.setLeft(0L);
        x.setRight(0L);
        x.setLevel(0L);
        save(x);
        flush();

        assertEquals(0L, (long) x.getLeft());
        assertEquals(0L, (long) x.getRight());

        this.repository.rebuildTree();
        refresh(x);
        printNode("x", x);
        assertEquals(1, (long) x.getLeft());
        assertEquals(2, (long) x.getRight());
        assertSecondTreeIntact();
    }

    @Test
    public void testDestroyTree() {
        repository.destroyTree();
        flushAndClear();

        TestSet a = this.findNode("a");
        TestSet e = this.findNode("e");
        TestSet b = this.findNode("b");
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet h = this.findNode("h");
        TestSet f = this.findNode("f");

        assertEquals(0, (long) a.getLeft());
        assertEquals(0, (long) a.getRight());
        assertEquals(0, (long) b.getLeft());
        assertEquals(0, (long) b.getRight());
        assertEquals(0, (long) c.getLeft());
        assertEquals(0, (long) c.getRight());
        assertEquals(0, (long) d.getLeft());
        assertEquals(0, (long) d.getRight());
        assertEquals(0, (long) e.getLeft());
        assertEquals(0, (long) e.getRight());
        assertEquals(0, (long) f.getLeft());
        assertEquals(0, (long) f.getRight());
        assertEquals(0, (long) g.getLeft());
        assertEquals(0, (long) g.getRight());
        assertEquals(0, (long) h.getLeft());
        assertEquals(0, (long) h.getRight());

        assertNull(this.getParent(a));
        assertEquals(this.getParent(b), a);
        assertEquals(this.getParent(c), a);
        assertEquals(this.getParent(d), b);
        assertEquals(this.getParent(e), b);
        assertEquals(this.getParent(f), c);
        assertEquals(this.getParent(g), c);
        assertEquals(this.getParent(h), g);

        assertEquals(0, (long) e.getLevel());
        assertEquals(0, (long) f.getLevel());
        assertEquals(0, (long) g.getLevel());
        assertEquals(0, (long) b.getLevel());
        assertEquals(0, (long) c.getLevel());
        assertEquals(0, (long) h.getLevel());
        assertEquals(0, (long) a.getLevel());
        assertEquals(0, (long) d.getLevel());

        assertSecondTreeIntact();

    }

    @Test
    public void testRebuildTree() {


        this.breakTree();
        this.resetParent("c");
        this.repository.rebuildTree();

        flushAndClear();

        TestSet a = this.findNode("a");
        TestSet e = this.findNode("e");
        TestSet b = this.findNode("b");
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet h = this.findNode("h");
        TestSet f = this.findNode("f");

        assertNull(this.getParent(a));
        assertEquals(this.getParent(b), a);
        assertNull(this.getParent(c));
        assertEquals(this.getParent(d), b);
        assertEquals(this.getParent(e), b);
        assertEquals(this.getParent(f), c);
        assertEquals(this.getParent(g), c);
        assertEquals(this.getParent(h), g);

        assertEquals(2, (long) e.getLevel());
        assertEquals(1, (long) f.getLevel());
        assertEquals(1, (long) g.getLevel());
        assertEquals(1, (long) b.getLevel());
        assertEquals(0, (long) c.getLevel());
        assertEquals(2, (long) h.getLevel());


        assertSecondTreeIntact();
    }

    @Test
    public void testRebuildWithSecondRoot() {

        TestSet i = this.createTestNode("i");
        TestSet j = this.createTestNode("j");
        TestSet k = this.createTestNode("k");
        TestSet a = this.findNode("a");
        this.repository.insertAsNextSiblingOf(i, a);
        this.repository.insertAsLastChildOf(j, i);
        this.repository.insertAsLastChildOf(k, i);

        breakTree();
        flushAndClear();
        this.repository.rebuildTree();

        a = this.findNode("a");
        TestSet b = this.findNode("b");
        TestSet c = this.findNode("c");
        TestSet d = this.findNode("d");
        TestSet e = this.findNode("e");
        TestSet g = this.findNode("g");
        TestSet f = this.findNode("f");
        TestSet h = this.findNode("h");

        i = findNode("i");
        j = findNode("j");
        k = findNode("k");

        printNode("i", i);
        printNode("j", j);
        printNode("k", k);

        assertNull(this.getParent(a));
        assertEquals(this.getParent(b), a);
        assertEquals(this.getParent(c), a);
        assertEquals(this.getParent(d), b);
        assertEquals(this.getParent(e), b);
        assertEquals(this.getParent(f), c);
        assertEquals(this.getParent(g), c);
        assertEquals(this.getParent(h), g);
        assertNull(this.getParent(i));
        assertEquals(this.getParent(j), i);
        assertEquals(this.getParent(k), i);

        assertSecondTreeIntact();
    }

}
