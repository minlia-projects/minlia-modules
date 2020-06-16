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
