/*
 *  The MIT License
 *
 *  Copyright (c) 2019 eXsio.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 *  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 *  the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 *  BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.minlia.module.nestedset.delegate.query.mem;

import com.google.common.collect.Maps;
import com.minlia.module.nestedset.config.mem.InMemoryNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.config.mem.discriminator.InMemoryTreeDiscriminator;
import com.minlia.module.nestedset.config.mem.identity.InMemoryNestedNodeIdentityGenerator;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public abstract class InMemoryNestedNodeQueryDelegate<ID extends Serializable, N extends NestedSet<ID>> {

    private final InMemoryTreeDiscriminator<ID, N> treeDiscriminator;

    private final InMemoryNestedNodeIdentityGenerator<ID> identityGenerator;

    protected final Set<N> nodes;

    protected final static Map<String, InMemoryNestedSetInsertingQueryDelegate.Setter> SETTERS = Maps.newHashMap();

    protected final static Map<String, InMemoryNestedSetInsertingQueryDelegate.Getter> GETTERS = Maps.newHashMap();

    static {
        SETTERS.put(NestedSet.RIGHT, (node, value) -> node.setRight((Long) value));
        SETTERS.put(NestedSet.LEFT, (node, value) -> node.setLeft((Long) value));
        SETTERS.put(NestedSet.LEVEL, (node, value) -> node.setLevel((Long) value));
        SETTERS.put(NestedSet.ID, (node, value) -> node.setId((Serializable) value));
        SETTERS.put(NestedSet.PARENT_ID, (node, value) -> node.setParentId((Serializable) value));

        GETTERS.put(NestedSet.RIGHT, NestedSet::getRight);
        GETTERS.put(NestedSet.LEFT, NestedSet::getLeft);
        GETTERS.put(NestedSet.LEVEL, NestedSet::getLevel);
        GETTERS.put(NestedSet.ID, NestedSet::getId);
        GETTERS.put(NestedSet.PARENT_ID, NestedSet::getParentId);
    }

    public InMemoryNestedNodeQueryDelegate(InMemoryNestedNodeRepositoryConfiguration<ID, N> configuration) {
        this.treeDiscriminator = configuration.getTreeDiscriminator();
        this.identityGenerator = configuration.getIdentityGenerator();
        this.nodes = configuration.getNodes();
    }

    protected Stream<N> nodesStream() {
        return treeDiscriminator != null ? nodes.stream().filter(treeDiscriminator::applies) : nodes.stream();
    }

    protected ID generateIdentity() {
        return identityGenerator.generateIdentity();
    }


    protected interface Setter<ID extends Serializable, N extends NestedSet<ID>> {
        void set(N node, Object value);
    }

    protected interface Getter<ID extends Serializable, N extends NestedSet<ID>> {
        Object get(N node);
    }

    protected Long getLong(String fieldname, N node) {
        return (Long) GETTERS.get(fieldname).get(node);
    }

    protected Serializable getSerializable(String fieldname, N node) {
        return (Serializable) GETTERS.get(fieldname).get(node);
    }

    protected void setLong(String fieldname, N node, Long value) {
        SETTERS.get(fieldname).set(node, value);
    }

    protected void setSerializable(String fieldname, N node, Serializable value) {
        SETTERS.get(fieldname).set(node, value);
    }
}
