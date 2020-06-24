//package com.minlia.module.nestedset.mem;
//
///*-
// * #%L
// * minlia
// * %%
// * Copyright (C) 2005 - 2020 Minlia Team
// * %%
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * #L%
// */
//
//import com.minlia.module.nestedset.DelegatingNestedNodeRepository;
//import com.minlia.module.nestedset.model.TestSet;
//import com.minlia.module.nestedset.qualifier.Mem;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import com.minlia.module.nestedset.base.NestedSetRepositoryRemovingTest;
//
//@Transactional
//public class InMemoryNestedSetRepositoryRemovingTest extends NestedSetRepositoryRemovingTest {
//
//    private InMemoryTestHelper helper;
//
//    @Autowired
//    @Mem
//    private DelegatingNestedNodeRepository<Long, TestSet> inMemoryRepository;
//
//    @Before
//    public void setup() {
//        helper = new InMemoryTestHelper();
//        helper.rollback();
//        this.repository = this.inMemoryRepository;
//    }
//
//    @Override
//    protected TestSet findNode(String symbol) {
//        return helper.findNode(symbol);
//    }
//
//    @Override
//    protected TestSet getParent(TestSet f) {
//        return helper.getParent(f);
//    }
//
//    @Override
//    protected void breakTree() {
//        helper.breakTree();
//    }
//
//    @Override
//    protected void resetParent(String symbol) {
//        helper.resetParent(symbol);
//    }
//
//    @Override
//    protected void removeTree() {
//        helper.removeTree();
//    }
//
//    @Override
//    protected void flushAndClear() {
//        helper.flushAndClear();
//    }
//
//    @Override
//    protected void flush() {
//        helper.flush();
//    }
//
//    @Override
//    protected void refresh(TestSet node) {
//        helper.refresh(node);
//    }
//
//    @Override
//    protected void save(TestSet node){
//        helper.save(node);
//    }
//}
