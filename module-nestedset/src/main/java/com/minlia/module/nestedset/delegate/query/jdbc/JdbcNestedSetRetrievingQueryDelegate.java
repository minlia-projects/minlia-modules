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

package com.minlia.module.nestedset.delegate.query.jdbc;

import com.google.common.collect.Lists;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.minlia.module.nestedset.config.jdbc.JdbcNestedNodeRepositoryConfiguration;
import com.minlia.module.nestedset.delegate.query.NestedSetRetrievingQueryDelegate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class JdbcNestedSetRetrievingQueryDelegate<ID extends Serializable, N extends NestedSet<ID>>
        extends JdbcNestedSetQueryDelegate<ID, N>
        implements NestedSetRetrievingQueryDelegate<ID, N> {

    public JdbcNestedSetRetrievingQueryDelegate(JdbcNestedNodeRepositoryConfiguration<ID, N> configuration) {
        super(configuration);
    }


    @Override
    public List<N> getTreeAsList(N node) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :left >= ? and :right <= ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getLeft());
                    preparedStatement.setObject(2, node.getRight());
                    setDiscriminatorParams(preparedStatement, 3);
                },
                rowMapper
        );
    }

    @Override
    public List<N> getChildren(N node) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :left >= ? and :right <= ? and :level = ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getLeft());
                    preparedStatement.setObject(2, node.getRight());
                    preparedStatement.setObject(3, node.getLevel() + 1);
                    setDiscriminatorParams(preparedStatement, 4);
                },
                rowMapper
        );
    }

    @Override
    public Optional<N> getParent(N node) {
        if (node.getLevel() > 0) {
            return jdbcTemplate.query(
                    getDiscriminatedQuery(
                            new Query("select * from :tableName where :left < ? and :right > ? and :level = ? order by :left asc").build()
                    ),
                    preparedStatement -> {
                        preparedStatement.setObject(1, node.getLeft());
                        preparedStatement.setObject(2, node.getRight());
                        preparedStatement.setObject(3, node.getLevel() - 1);
                        setDiscriminatorParams(preparedStatement, 4);
                    },
                    rowMapper
            ).stream().findFirst();
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<N> getParents(N node) {
        if (node.getLevel() > 0) {
            return jdbcTemplate.query(
                    getDiscriminatedQuery(
                            new Query("select * from :tableName where :left < ? and :right > ? order by :left desc").build()
                    ),
                    preparedStatement -> {
                        preparedStatement.setObject(1, node.getLeft());
                        preparedStatement.setObject(2, node.getRight());
                        setDiscriminatorParams(preparedStatement, 3);
                    },
                    rowMapper
            );
        } else {
            return Lists.newLinkedList();
        }
    }

    @Override
    public Optional<N> getPrevSibling(N node) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :right = ? and :level = ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getLeft() - 1);
                    preparedStatement.setObject(2, node.getLevel());
                    setDiscriminatorParams(preparedStatement, 3);
                },
                rowMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<N> getNextSibling(N node) {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :left = ? and :level = ? order by :left asc").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, node.getRight() + 1);
                    preparedStatement.setObject(2, node.getLevel());
                    setDiscriminatorParams(preparedStatement, 3);
                },
                rowMapper
        ).stream().findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<NestedSetDetail<ID>> getNodeInfo(ID nodeId) {
        NestedSetDetail<ID> info = jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select :id, :parentId, :left, :right, :level from :tableName where :id = ?").build()
                ),
                preparedStatement -> {
                    preparedStatement.setObject(1, nodeId);
                    setDiscriminatorParams(preparedStatement, 2);
                },
                (ResultSetExtractor<NestedSetDetail<ID>>) rs -> {
                    if(!rs.next()) {
                        return null;
                    }
                    return new NestedSetDetail((ID) rs.getObject(id), (ID) rs.getObject(parentId), rs.getLong(left), rs.getLong(right), rs.getLong(level));
                }
        );
        return Optional.ofNullable(info);
    }

    @Override
    public Optional<N> findFirstRoot() {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :level = 0 order by :left asc").build()
                ),
                preparedStatement -> setDiscriminatorParams(preparedStatement, 1),
                rowMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<N> findLastRoot() {
        return jdbcTemplate.query(
                getDiscriminatedQuery(
                        new Query("select * from :tableName where :level = 0 order by :left desc").build()
                ),
                preparedStatement -> setDiscriminatorParams(preparedStatement, 1),
                rowMapper
        ).stream().findFirst();
    }
}
