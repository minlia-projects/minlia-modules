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

@Transactional
public abstract class NestedSetRepositoryRemovingTest extends FunctionalNestedSetTest {

    @Test
    public void testRemoveSubtreeWithoutChildren() {

        TestSet d = this.findNode("d");
        this.repository.removeSubtree(d);
        TestSet a = this.findNode("a");
        TestSet e = this.findNode("e");
        TestSet b = this.findNode("b");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet h = this.findNode("h");
        TestSet f = this.findNode("f");

        assertEquals(3, (long) e.getLeft());
        assertEquals(4, (long) e.getRight());
        assertEquals(5, (long) b.getRight());
        assertEquals(10, (long) h.getLeft());
        assertEquals(11, (long) h.getRight());
        assertEquals(14, (long) a.getRight());
        assertEquals(6, (long) c.getLeft());
        assertEquals(13, (long) c.getRight());
        assertEquals(9, (long) g.getLeft());
        assertEquals(12, (long) g.getRight());
        assertSecondTreeIntact();

    }

    @Test
    public void testRemoveSubtree() {

        TestSet b = this.findNode("b");
        this.repository.removeSubtree(b);
        TestSet a = this.findNode("a");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet h = this.findNode("h");
        TestSet f = this.findNode("f");

        assertEquals(6, (long) h.getLeft());
        assertEquals(7, (long) h.getRight());
        assertEquals(10, (long) a.getRight());
        assertEquals(2, (long) c.getLeft());
        assertEquals(9, (long) c.getRight());
        assertEquals(5, (long) g.getLeft());
        assertEquals(8, (long) g.getRight());
        assertSecondTreeIntact();

    }

    @Test
    public void testRemoveSingleNodeThatHasChildren() {

        TestSet b = this.findNode("b");
        this.repository.removeSingle(b);
        TestSet a = this.findNode("a");
        TestSet e = this.findNode("e");
        TestSet d = this.findNode("d");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet h = this.findNode("h");

        assertEquals(2, (long) d.getLeft());
        assertEquals(3, (long) d.getRight());
        assertEquals(4, (long) e.getLeft());
        assertEquals(5, (long) e.getRight());
        assertEquals(10, (long) h.getLeft());
        assertEquals(11, (long) h.getRight());
        assertEquals(14, (long) a.getRight());
        assertEquals(6, (long) c.getLeft());
        assertEquals(13, (long) c.getRight());
        assertEquals(9, (long) g.getLeft());
        assertEquals(12, (long) g.getRight());
        assertEquals(1, (long) d.getLevel());
        assertEquals(1, (long) e.getLevel());
        assertSecondTreeIntact();
    }

    @Test
    public void testRemoveSingleNode() {

        TestSet d = this.findNode("d");
        this.repository.removeSingle(d);
        TestSet a = this.findNode("a");
        TestSet g = this.findNode("g");
        TestSet c = this.findNode("c");
        TestSet e = this.findNode("e");

        assertEquals(3, (long) e.getLeft());
        assertEquals(4, (long) e.getRight());
        assertEquals(14, (long) a.getRight());
        assertEquals(6, (long) c.getLeft());
        assertEquals(13, (long) c.getRight());
        assertEquals(9, (long) g.getLeft());
        assertEquals(12, (long) g.getRight());
        assertSecondTreeIntact();

    }

}
