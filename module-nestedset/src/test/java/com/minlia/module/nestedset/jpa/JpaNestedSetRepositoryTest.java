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
package com.minlia.module.nestedset.jpa;

import com.minlia.module.nestedset.DelegatingNestedNodeRepository;
import com.minlia.module.nestedset.model.TestSet;
import com.minlia.module.nestedset.qualifier.Jpa;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.minlia.module.nestedset.base.NestedSetRepositoryTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class JpaNestedSetRepositoryTest extends NestedSetRepositoryTest {

    private JpaTestHelper helper;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    @Jpa
    private DelegatingNestedNodeRepository<Long, TestSet> jpaRepository;

    @Before
    public void setup() {
        helper = new JpaTestHelper(em);
        this.repository = this.jpaRepository;
    }

    @Override
    protected TestSet findNode(String symbol) {
        return helper.findNode(symbol);
    }

    @Override
    protected TestSet getParent(TestSet f) {
        return helper.getParent(f);
    }

    @Override
    protected void breakTree() {
        helper.breakTree();
    }

    @Override
    protected void removeTree() {
        helper.removeTree();
    }

    @Override
    protected void flushAndClear() {
        helper.flushAndClear();
    }

    @Override
    protected void flush() {
        helper.flush();
    }

    @Override
    protected void resetParent(String symbol) {
        helper.resetParent(symbol);
    }

    @Override
    protected void refresh(TestSet node) {
        helper.refresh(node);
    }

    @Override
    protected void save(TestSet node){
        helper.save(node);
    }
}
