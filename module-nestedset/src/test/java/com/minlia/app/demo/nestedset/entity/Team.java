package com.minlia.app.demo.nestedset.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.nestedset.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.minlia.module.nestedset.model.NestedSet;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tdl_team_test")
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 1)
public class Team implements NestedSet<Long> {

    @IdColumn
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @LeftColumn
    @Column(name = "lft", nullable = false)
    private Long left;

    @RightColumn
    @Column(name = "rgt", nullable = false)
    private Long right;

    @LevelColumn
    @Column(name = "level", nullable = false)
    private Long level;


    @ParentIdColumn
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "discriminator", nullable = false)
    private String discriminator;



    private String accountLevel;

    //    public DemoNode() {
//    }
//
//    public DemoNode(Long id, Long treeLeft, Long treeLevel, Long treeRight, String name, Long parentId, String discriminator) {
//        this.id = id;
//        this.name = name;
//        this.treeLeft = treeLeft;
//        this.treeRight = treeRight;
//        this.treeLevel = treeLevel;
//        this.parentId = parentId;
//        this.discriminator = discriminator;
//    }
//
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Long getLeft() {
        return left;
    }

    @JsonIgnore
    public Long getRight() {
        return right;
    }

    @JsonIgnore
    public Long getLevel() {
        return level;
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

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public void setLeft(Long treeLeft) {
        this.left = treeLeft;
    }

    public void setRight(Long treeRight) {
        this.right = treeRight;
    }

    public void setLevel(Long treeLevel) {
        this.level = treeLevel;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
//
//    @Override
//    public String toString() {
//        return MoreObjects.toStringHelper(this)
//                .add("id", id)
//                .add("name", name)
//                .add("treeLeft", treeLeft)
//                .add("treeRight", treeRight)
//                .add("treeLevel", treeLevel)
//                .add("parentId", parentId)
//                .add("discriminator", discriminator)
//                .toString();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof DemoNode)) return false;
//        DemoNode testNode = (DemoNode) o;
//        return Objects.equals(getId(), testNode.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }

    public static Team fromResultSet(ResultSet resultSet) throws SQLException {
        Team n = new Team();
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

    public Team copy() {
        return new Team(id, name, left, right, level, parentId, discriminator,accountLevel);
    }
}
