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
