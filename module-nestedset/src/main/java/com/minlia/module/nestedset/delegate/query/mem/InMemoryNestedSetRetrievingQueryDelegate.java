//
//package com.minlia.module.nestedset.delegate.query.mem;
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
//import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
//import com.minlia.module.nestedset.model.NestedSet;
//import com.minlia.module.nestedset.model.NestedSetDetail;
//import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;
//
//import java.io.Serializable;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//public class InMemoryNestedSetRetrievingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
//        extends InMemoryNestedNodeQueryDelegate<ID, N>
//        implements NestedSetRetrievingQueryDelegate<ID, N> {
//
//    public InMemoryNestedSetRetrievingQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
//        super(configuration);
//    }
//
//
//    @Override
//    public List<N> getTreeAsList(N entity) {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.LEFT, n) >= entity.getLeft())
//                .filter(n -> getLong(NestedSet.RIGHT, n) <= entity.getRight())
//                .sorted(Comparator.comparing(NestedSet::getLeft))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<N> getChildren(N entity) {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.LEFT, n) >= entity.getLeft())
//                .filter(n -> getLong(NestedSet.RIGHT, n) <= entity.getRight())
//                .filter(n -> getLong(NestedSet.LEVEL, n).equals(entity.getLevel() + 1))
//                .sorted(Comparator.comparing(NestedSet::getLeft))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Optional<N> getParent(N entity) {
//        if (entity.getLevel() > 0) {
//            return nodesStream()
//                    .filter(n -> getLong(NestedSet.LEFT, n) < entity.getLeft())
//                    .filter(n -> getLong(NestedSet.RIGHT, n) > entity.getRight())
//                    .filter(n -> getLong(NestedSet.LEVEL, n).equals(entity.getLevel() - 1))
//                    .min(Comparator.comparing(NestedSet::getLeft));
//        } else {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public List<N> getParents(N entity) {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.LEFT, n) < entity.getLeft())
//                .filter(n -> getLong(NestedSet.RIGHT, n) > entity.getRight())
//                .sorted(Comparator.<N, Long>comparing(NestedSet::getLeft).reversed())
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Optional<N> getPrevSibling(N entity) {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.RIGHT, n).equals(entity.getLeft() - 1))
//                .filter(n -> getLong(NestedSet.LEVEL, n).equals(entity.getLevel()))
//                .min(Comparator.comparing(NestedSet::getLeft));
//    }
//
//    @Override
//    public Optional<N> getNextSibling(N entity) {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.LEFT, n).equals(entity.getRight() + 1))
//                .filter(n -> getLong(NestedSet.LEVEL, n).equals(entity.getLevel()))
//                .min(Comparator.comparing(NestedSet::getLeft));
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Optional<NestedSetDetail<ID>> getNestedSetDetail(ID nodeId) {
//        Optional<N> node = nodesStream()
//                .filter(n -> getSerializable(NestedSet.ID, n).equals(nodeId))
//                .findFirst();
//        return node.map(n -> new NestedSetDetail<>(
//                        (ID) getSerializable(NestedSet.ID, n),
//                        (ID) getSerializable(NestedSet.PARENT_ID, n),
//                        getLong(NestedSet.LEFT, n),
//                        getLong(NestedSet.RIGHT, n),
//                        getLong(NestedSet.LEVEL, n)
//                ));
//    }
//
//    @Override
//    public Optional<N> findFirstRoot() {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.LEVEL, n).equals(0L))
//                .min(Comparator.comparing(NestedSet::getLeft));
//    }
//
//    @Override
//    public Optional<N> findLastRoot() {
//        return nodesStream()
//                .filter(n -> getLong(NestedSet.LEVEL, n).equals(0L))
//                .max(Comparator.comparing(NestedSet::getLeft));
//    }
//}
