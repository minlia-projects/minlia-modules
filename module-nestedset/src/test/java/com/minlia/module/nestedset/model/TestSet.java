package com.minlia.module.nestedset.model;

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

import com.google.common.base.MoreObjects;
import com.minlia.module.nestedset.annotation.NestedSetModel;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


@Entity
@Table(name = "nested_nodes")
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 1)
@NestedSetModel(idFieldName = "id", leftFieldName = "treeLeft", rightFieldName = "treeRight", parentIdFieldName = "parentId", levelFieldName = "treeLevel")
public class TestSet implements NestedSet<Long> {

    //    @IdColumn
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "node_name", nullable = false, unique = true)
    private String name;

    //    @LeftColumn
    @Column(name = "tree_left", nullable = false)
    private Long treeLeft;

    //    @RightColumn
    @Column(name = "tree_right", nullable = false)
    private Long treeRight;

    //    @LevelColumn
    @Column(name = "tree_level", nullable = false)
    private Long treeLevel;

    //    @ParentIdColumn
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "discriminator", nullable = false)
    private String discriminator;

    public TestSet() {
    }

    public TestSet(Long id, Long treeLeft, Long treeLevel, Long treeRight, String name, Long parentId, String discriminator) {
        this.id = id;
        this.name = name;
        this.treeLeft = treeLeft;
        this.treeRight = treeRight;
        this.treeLevel = treeLevel;
        this.parentId = parentId;
        this.discriminator = discriminator;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getLeft() {
        return treeLeft;
    }

    @Override
    public Long getRight() {
        return treeRight;
    }

    @Override
    public Long getLevel() {
        return treeLevel;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getName() {
        return name;
    }

    public TestSet setName(String name) {
        this.name = name;
        return this;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    @Override
    public void setLeft(Long treeLeft) {
        this.treeLeft = treeLeft;
    }

    @Override
    public void setRight(Long treeRight) {
        this.treeRight = treeRight;
    }

    @Override
    public void setLevel(Long treeLevel) {
        this.treeLevel = treeLevel;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("treeLeft", treeLeft)
                .add("treeRight", treeRight)
                .add("treeLevel", treeLevel)
                .add("parentId", parentId)
                .add("discriminator", discriminator)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSet)) return false;
        TestSet testNode = (TestSet) o;
        return Objects.equals(getId(), testNode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static TestSet fromResultSet(ResultSet resultSet) throws SQLException {
        TestSet n = new TestSet();
        n.setId(resultSet.getLong("ID"));
        n.setLeft(resultSet.getLong("TREE_LEFT"));
        n.setLevel(resultSet.getLong("TREE_LEVEL"));
        n.setRight(resultSet.getLong("TREE_RIGHT"));
        n.setName(resultSet.getString("NODE_NAME"));
        n.setParentId(resultSet.getLong("PARENT_ID"));
        if (resultSet.wasNull()) {
            n.setParentId(null);
        }
        n.setDiscriminator(resultSet.getString("DISCRIMINATOR"));
        return n;
    }

    public TestSet copy() {
        return new TestSet(id, treeLeft, treeLevel, treeRight, name, parentId, discriminator);
    }
}
