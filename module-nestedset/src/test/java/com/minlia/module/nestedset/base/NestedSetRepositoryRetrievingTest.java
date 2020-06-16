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
import com.minlia.module.nestedset.model.Tree;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
public abstract class NestedSetRepositoryRetrievingTest extends FunctionalNestedSetTest {

    @Test
    public void testGetParents() {
        TestSet h = this.findNode("h");
        List<TestSet> parents = (List<TestSet>) this.repository.getParents(h);
        assertEquals(3, parents.size());
        assertEquals("g", parents.get(0).getName());
        assertEquals("c", parents.get(1).getName());
        assertEquals("a", parents.get(2).getName());
        assertSecondTreeIntact();
    }

    @Test
    public void testGetPrevSibling() {
        TestSet c = this.findNode("c");
        Optional<TestSet> prevSibling = this.repository.getPrevSibling(c);
        assertTrue(prevSibling.isPresent());
        assertEquals("b", prevSibling.get().getName());
    }

    @Test
    public void testGetNextSibling() {
        TestSet b = this.findNode("b");
        Optional<TestSet> nextSibling = this.repository.getNextSibling(b);
        assertTrue(nextSibling.isPresent());
        assertEquals("c", nextSibling.get().getName());
    }

    @Test
    public void testGetPrevSiblingRoot() {

        TestSet y = this.createTestNode("y");
        try {
            y.setLeft(0L);
            y.setRight(0L);
            y.setLevel(0L);
            y.setParentId(null);
            save(y);
            flush();
            this.repository.rebuildTree();
            refresh(y);
        } catch (InvalidNodesHierarchyException ex) {
            fail("something went wrong while creating a new root level node:" + ex.getMessage());
        }

        System.out.println(y);
        // ensure node y was built as a root level node
        assertEquals(0, (long) y.getLevel());
        assertNull(y.getParentId());
        assertEquals(17, (long) y.getLeft());
        assertEquals(18, (long) y.getRight());

        Optional<TestSet> prevSiblingRoot = this.repository.getPrevSibling(y);
        assertTrue(prevSiblingRoot.isPresent());
        assertEquals("a", prevSiblingRoot.get().getName());
        assertSecondTreeIntact();
    }

    @Test
    public void testGetNextSiblingRoot() {

        TestSet z = this.createTestNode("z");
        try {
            z.setLeft(0L);
            z.setRight(0L);
            z.setLevel(0L);
            save(z);
            flush();
            this.repository.rebuildTree();
            refresh(z);
        } catch (InvalidNodesHierarchyException ex) {
            fail("something went wrong while creating a new root level node:" + ex.getMessage());
        }

        // ensure node z was built as a root level node
        assertEquals(0, (long) z.getLevel());
        assertNull(z.getParentId());
        assertEquals(17, (long) z.getLeft());
        assertEquals(18, (long) z.getRight());

        TestSet a = this.findNode("a");

        Optional<TestSet> nextSiblingRoot = this.repository.getNextSibling(a);
        assertTrue(nextSiblingRoot.isPresent());
        assertEquals("z", nextSiblingRoot.get().getName());
        assertSecondTreeIntact();
    }

    @Test
    public void testGetTree() {
        Tree<Long, TestSet> tree = this.repository.getTree(this.findNode("a"));
        assertEquals("a", tree.getNode().getName());
        assertEquals("b", tree.getChildren().get(0).getNode().getName());
        assertEquals(2, tree.getChildren().size());
        assertEquals(2, tree.getChildren().get(0).getChildren().size());
        assertEquals(2, tree.getChildren().get(1).getChildren().size());
        assertEquals(1, tree.getChildren().get(1).getChildren().get(1).getChildren().size());
        assertTrue(tree.getChildren().get(1).getChildren().get(0).getChildren().isEmpty());
        assertTrue(tree.getChildren().get(0).getChildren().get(0).getChildren().isEmpty());
        assertSecondTreeIntact();
    }

    @Test
    public void testGetTreeAsList() {
        List<TestSet> list = (List<TestSet>) this.repository.getTreeAsList(this.findNode("a"));
        assertEquals(8, list.size());
        assertSecondTreeIntact();
    }

    @Test
    public void testGetParent() {
        TestSet b = this.findNode("b");
        Optional<TestSet> parent = this.repository.getParent(b);
        assertTrue(parent.isPresent());
        assertEquals("a", parent.get().getName());
        assertSecondTreeIntact();
    }

    @Test
    public void testGetChildren() {

        List result = (List) this.repository.getChildren(this.findNode("a"));
        assertEquals(2, result.size());
        assertSecondTreeIntact();
    }
}
