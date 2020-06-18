package com.minlia.module.nestedset.jdbc;

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

import com.minlia.module.nestedset.base.TestHelper;
import com.minlia.module.nestedset.model.TestSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import com.minlia.module.nestedset.delegate.query.jdbc.JdbcKeyHolder;

import javax.sql.DataSource;

public class JdbcTestHelper implements TestHelper {

    private final JdbcTemplate jdbcTemplate;

    JdbcTestHelper(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TestSet findNode(String symbol) {
        return jdbcTemplate.query("select id, node_name, tree_left, tree_right, tree_level, parent_id, discriminator from nested_nodes where node_name = '" + symbol + "'",
                (resultSet, i) -> TestSet.fromResultSet(resultSet)).stream().findFirst().orElse(null);
    }

    @Override
    public TestSet getParent(TestSet f) {
        refresh(f);
        TestSet parent = null;
        Long parentId = f.getParentId();
        if (parentId != null) {
            parent = findNode(parentId);
        }
        System.out.println(String.format("Parent of %s is %s", f.getName(), parent != null ? parent.getName() : "null"));
        return parent;
    }

    public TestSet findNode(Long id) {
        return jdbcTemplate.query("select id, node_name, tree_left, tree_right, tree_level, parent_id, discriminator from nested_nodes where id = '" + id + "'",
                (resultSet, i) -> TestSet.fromResultSet(resultSet)).stream().findFirst().orElse(null);
    }

    @Override
    public void breakTree() {
        jdbcTemplate.execute("update nested_nodes set tree_left = 0, tree_right = 0, tree_level = 0 where discriminator = 'tree_1'");
    }

    @Override
    public void resetParent(String symbol) {
        jdbcTemplate.execute("update nested_nodes set parent_id = null where node_name='"+symbol+"' and discriminator = 'tree_1'");
    }

    @Override
    public void removeTree() {
        jdbcTemplate.execute("delete from nested_nodes where discriminator = 'tree_1'");
    }

    public void flushAndClear() {
    }

    public void flush() {
    }

    public void refresh(TestSet node) {
        if(node.getId() == null) {
            return;
        }
        TestSet refreshed = findNode(node.getId());
        node.setParentId(refreshed.getParentId());
        node.setName(refreshed.getName());
        node.setRight(refreshed.getRight());
        node.setLeft(refreshed.getLeft());
        node.setLevel(refreshed.getLevel());
        node.setDiscriminator(refreshed.getDiscriminator());
    }

    public void save(TestSet node) {
        KeyHolder keyHolder = new JdbcKeyHolder();
        jdbcTemplate.update(con -> {
            String insertQuery = String.format("insert into nested_nodes(id, tree_left, tree_level, tree_right, node_name, parent_id, discriminator) values(next value for SEQ,%s,%s,%s,'%s',%s,'%s')",
                    node.getLeft(), node.getLevel(), node.getRight(), node.getName(), node.getParentId(), node.getDiscriminator()
            );
            return con.prepareStatement(insertQuery, new String[]{"id"});
        }, keyHolder);
        node.setId((Long) keyHolder.getKey());
    }
}
