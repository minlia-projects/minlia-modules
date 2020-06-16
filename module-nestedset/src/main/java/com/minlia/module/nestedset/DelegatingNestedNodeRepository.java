package com.minlia.module.nestedset;

/*-
 * #%L
 * minlia
 * %%
 * Copyright (C) 2005 - 2020 Minlia, Inc
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

import com.minlia.module.nestedset.delegate.*;
import com.minlia.module.nestedset.exception.InvalidNodeException;
import com.minlia.module.nestedset.exception.InvalidParentException;
import com.minlia.module.nestedset.exception.RepositoryLockedException;
import org.springframework.transaction.annotation.Transactional;
import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.NestedSetDetail;
import com.minlia.module.nestedset.model.Tree;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Transactional
public class DelegatingNestedNodeRepository<ID extends Serializable, ENTITY extends NestedSet<ID>> implements NestedNodeRepository<ID, ENTITY> {

    private final NestedSetCreator<ID, ENTITY> creator;

    private final NestedSetMover<ID, ENTITY> mover;

    private final NestedSetRemover<ID, ENTITY> remover;

    private final NestedSetRetriever<ID, ENTITY> retriever;

    private final NestedSetRebuilder<ID, ENTITY> rebuilder;

    private final Lock<ID, ENTITY> lock;

    private boolean allowNullableTreeFields = false;

    public DelegatingNestedNodeRepository(NestedSetMover<ID, ENTITY> mover,
                                          NestedSetRemover<ID, ENTITY> remover,
                                          NestedSetRetriever<ID, ENTITY> retriever,
                                          NestedSetRebuilder<ID, ENTITY> rebuilder,
                                          NestedSetCreator<ID, ENTITY> creator,
                                          Lock<ID, ENTITY> lock) {
        this.creator = creator;
        this.mover = mover;
        this.remover = remover;
        this.retriever = retriever;
        this.rebuilder = rebuilder;
        this.lock = lock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAsFirstChildOf(ENTITY node, ENTITY parent) {
        lockNode(node, () -> insertOrMove(node, parent, NestedSetHierarchyManipulator.Mode.FIRST_CHILD));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAsLastChildOf(ENTITY node, ENTITY parent) {
        lockNode(node, () -> insertOrMove(node, parent, NestedSetHierarchyManipulator.Mode.LAST_CHILD));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAsNextSiblingOf(ENTITY node, ENTITY parent) {
        lockNode(node, () -> insertOrMove(node, parent, NestedSetHierarchyManipulator.Mode.NEXT_SIBLING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAsPrevSiblingOf(ENTITY node, ENTITY parent) {
        lockNode(node, () -> insertOrMove(node, parent, NestedSetHierarchyManipulator.Mode.PREV_SIBLING));
    }

    private void insertOrMove(ENTITY node, ENTITY parent, NestedSetHierarchyManipulator.Mode mode) {
        if (parent.getId() == null) {
            throw new InvalidParentException("Cannot insert or move to a parent that has null id");
        }
        Optional<NestedSetDetail<ID>> parentInfo = retriever.getNestedSetDetail(parent.getId());
        if (!parentInfo.isPresent()) {
            throw new InvalidParentException(String.format("Cannot insert or move to non existent parent. Parent id: %s", parent.getId()));
        }
        if (node.getId() != null) {
            Optional<NestedSetDetail<ID>> nodeInfo = retriever.getNestedSetDetail(node.getId());
            if (nodeInfo.isPresent()) {
                boolean nodeInfoValid = isNestedSetDetailValid(nodeInfo.get());
                if (nodeInfoValid) {
                    this.mover.move(nodeInfo.get(), parentInfo.get(), mode);
                } else if (allowNullableTreeFields) {
                    this.creator.create(node, parentInfo.get(), mode);
                } else {
                    throw new InvalidNodeException(String.format("Current configuration doesn't allow nullable tree fields: %s", nodeInfo.get()));
                }
            } else {
                this.creator.create(node, parentInfo.get(), mode);
            }
        } else {
            this.creator.create(node, parentInfo.get(), mode);
        }
    }

    private boolean isNestedSetDetailValid(NestedSetDetail<ID> nestedSetDetail) {
        return (nestedSetDetail.getLeft() != null && nestedSetDetail.getRight() != null && nestedSetDetail.getLeft() > 0 && nestedSetDetail.getRight() > 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSingle(ENTITY entity) {
        lockNode(entity, () -> {
            Optional<NestedSetDetail<ID>> nodeInfo = retriever.getNestedSetDetail(entity.getId());
            if (nodeInfo.isPresent()) {
                this.remover.removeSingle(nodeInfo.get());
            } else {
                throw new InvalidNodeException(String.format("Couldn't remove node, was it already removed?: %s", entity));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSubtree(ENTITY entity) {
        lockNode(entity, () -> {
            Optional<NestedSetDetail<ID>> nodeInfo = retriever.getNestedSetDetail(entity.getId());
            if (nodeInfo.isPresent()) {
                this.remover.removeSubtree(nodeInfo.get());
            } else {
                throw new InvalidNodeException(String.format("Couldn't remove node subtree, was it already removed?: %s", entity));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> getTreeAsList(ENTITY entity) {
        return this.retriever.getTreeAsList(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> getChildren(ENTITY entity) {
        return this.retriever.getChildren(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> getParent(ENTITY entity) {
        return this.retriever.getParent(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> getPrevSibling(ENTITY entity) {
        return this.retriever.getPrevSibling(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ENTITY> getNextSibling(ENTITY entity) {
        return this.retriever.getNextSibling(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tree<ID, ENTITY> getTree(ENTITY entity) {
        return this.retriever.getTree(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ENTITY> getParents(ENTITY entity) {
        return this.retriever.getParents(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rebuildTree() {
        lockRepository(rebuilder::rebuildTree);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyTree() {
        lockRepository(rebuilder::destroyTree);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAsFirstRoot(ENTITY entity) {
        lockNode(entity, () -> {
            Optional<ENTITY> firstRoot = retriever.findFirstRoot();
            if (firstRoot.isPresent()) {
                if (differentNodes(entity, firstRoot.get())) {
                    insertOrMove(entity, firstRoot.get(), NestedSetHierarchyManipulator.Mode.PREV_SIBLING);
                }
            } else {
                insertAsFirstNode(entity);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAsLastRoot(ENTITY entity) {
        lockNode(entity, () -> {
            Optional<ENTITY> lastRoot = retriever.findLastRoot();
            if (lastRoot.isPresent()) {
                if (differentNodes(entity, lastRoot.get())) {
                    insertOrMove(entity, lastRoot.get(), NestedSetHierarchyManipulator.Mode.NEXT_SIBLING);
                }
            } else {
                insertAsFirstNode(entity);
            }
        });
    }

    private boolean differentNodes(ENTITY entity, ENTITY firstRoot) {
        return !firstRoot.getId().equals(entity.getId());
    }

    private void insertAsFirstNode(ENTITY entity) {
        creator.createAsFirstNode(entity);
    }

    public boolean isAllowNullableTreeFields() {
        return allowNullableTreeFields;
    }

    public void setAllowNullableTreeFields(boolean allowNullableTreeFields) {
        this.allowNullableTreeFields = allowNullableTreeFields;
    }


    private void lockNode(ENTITY entity, NestedSetModifier modifier) {
        if (!lock.lockNode(entity)) {
            throw new RepositoryLockedException(String.format("Nested Node Repository is locked for Node %s. Try again later.", entity));
        }
        try {
            modifier.modifyTree();
        } finally {
            lock.unlockNode(entity);
        }
    }

    private void lockRepository(NestedSetModifier modifier) {
        if (!lock.lockRepository()) {
            throw new RepositoryLockedException("Nested Node Repository is locked. Try again later.");
        }
        try {
            modifier.modifyTree();
        } finally {
            lock.unlockRepository();
        }
    }

    private interface NestedSetModifier {
        void modifyTree();
    }
}
