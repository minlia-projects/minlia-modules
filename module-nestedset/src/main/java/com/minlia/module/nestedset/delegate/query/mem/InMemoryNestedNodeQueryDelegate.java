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
//import com.google.common.collect.Maps;
//import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
//import com.minlia.module.nestedset.model.NestedSet;
//import com.minlia.module.nestedset.config.mem.discriminator.InMemoryTreeDiscriminator;
//import com.minlia.module.nestedset.config.mem.identity.InMemoryNestedNodeIdentityGenerator;
//
//import java.io.Serializable;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Stream;
//
//@SuppressWarnings("unchecked")
//public abstract class InMemoryNestedNodeQueryDelegate<ID extends Serializable, N extends NestedSet<ID>> {
//
//    private final InMemoryTreeDiscriminator<ID, N> treeDiscriminator;
//
//    private final InMemoryNestedNodeIdentityGenerator<ID> identityGenerator;
//
//    protected final Set<N> nodes;
//
//    protected final static Map<String, InMemoryNestedSetInsertingQueryDelegate.Setter> SETTERS = Maps.newHashMap();
//
//    protected final static Map<String, InMemoryNestedSetInsertingQueryDelegate.Getter> GETTERS = Maps.newHashMap();
//
//    static {
//        SETTERS.put(NestedSet.RIGHT, (node, value) -> node.setRight((Long) value));
//        SETTERS.put(NestedSet.LEFT, (node, value) -> node.setLeft((Long) value));
//        SETTERS.put(NestedSet.LEVEL, (node, value) -> node.setLevel((Long) value));
//        SETTERS.put(NestedSet.ID, (node, value) -> node.setId((Serializable) value));
//        SETTERS.put(NestedSet.PARENT_ID, (node, value) -> node.setParentId((Serializable) value));
//
//        GETTERS.put(NestedSet.RIGHT, NestedSet::getRight);
//        GETTERS.put(NestedSet.LEFT, NestedSet::getLeft);
//        GETTERS.put(NestedSet.LEVEL, NestedSet::getLevel);
//        GETTERS.put(NestedSet.ID, NestedSet::getId);
//        GETTERS.put(NestedSet.PARENT_ID, NestedSet::getParentId);
//    }
//
//    public InMemoryNestedNodeQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
//        this.treeDiscriminator = configuration.getTreeDiscriminator();
//        this.identityGenerator = configuration.getIdentityGenerator();
//        this.nodes = configuration.getNodes();
//    }
//
//    protected Stream<N> nodesStream() {
//        return treeDiscriminator != null ? nodes.stream().filter(treeDiscriminator::applies) : nodes.stream();
//    }
//
//    protected ID generateIdentity() {
//        return identityGenerator.generateIdentity();
//    }
//
//
//    protected interface Setter<ID extends Serializable, N extends NestedSet<ID>> {
//        void set(N node, Object value);
//    }
//
//    protected interface Getter<ID extends Serializable, N extends NestedSet<ID>> {
//        Object get(N node);
//    }
//
//    protected Long getLong(String fieldname, N node) {
//        return (Long) GETTERS.get(fieldname).get(node);
//    }
//
//    protected Serializable getSerializable(String fieldname, N node) {
//        return (Serializable) GETTERS.get(fieldname).get(node);
//    }
//
//    protected void setLong(String fieldname, N node, Long value) {
//        SETTERS.get(fieldname).set(node, value);
//    }
//
//    protected void setSerializable(String fieldname, N node, Serializable value) {
//        SETTERS.get(fieldname).set(node, value);
//    }
//}
