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

import com.minlia.module.nestedset.model.NestedSet;
import com.minlia.module.nestedset.model.Tree;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Primary API. Serves as an entry point to all Tree manipulation and some common data retrieval actions.
 * It does not cover every possible data retrieval scenarios - especially when joining the NestedNode Entity/Table/Object
 * with other objects. If you have a requirement that is not covered by the Repository, feel free to experiment and use
 * the Nested Set Model features to retrieve the data the way you want.
 * <p>
 * As for Tree manipulation, the Repository covers all scenarios and should be the only entry point. I you're missing a feature
 * in that area, please consider raising a request in github.
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <ENTITY>  - Nested Node Class
 */
public interface NestedNodeRepository<ID extends Serializable, ENTITY extends NestedSet<ID>> {

    /**
     * Inserts or Updates (depending if id is null or not) the node as the first child of given parent.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     *
     * @param node   - target Node
     * @param parent - parent Node
     */
    void insertAsFirstChildOf(ENTITY node, ENTITY parent);

    /**
     * Inserts or Updates (depending if id is null or not) the node as the last child of given parent.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     *
     * @param node   - target Node
     * @param parent - parent Node
     */
    void insertAsLastChildOf(ENTITY node, ENTITY parent);

    /**
     * Inserts or Updates (depending if id is null or not) the node as a next sibling of given parent.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     *
     * @param node   - target Node
     * @param parent - parent Node
     */
    void insertAsNextSiblingOf(ENTITY node, ENTITY parent);

    /**
     * Inserts or Updates (depending if id is null or not) the node as a previous sibling of given parent.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     *
     * @param node   - target Node
     * @param parent - parent Node
     */
    void insertAsPrevSiblingOf(ENTITY node, ENTITY parent);

    /**
     * Removes Single Node. All Children/Descendants of the removed Node are assigned to the parent of the removed Node.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     *
     * @param node - target Node
     */
    void removeSingle(ENTITY node);

    /**
     * Removes Node and cascades the operation to all Children/Descendants of the Node.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     *
     * @param node - target Node
     */
    void removeSubtree(ENTITY node);

    /**
     * Returns a flat list of all Node's direct Children/Sescendants sorted by the LEFT asc.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - parent Node
     * @return List of Child Nodes
     */
    List<ENTITY> getChildren(ENTITY node);

    /**
     * Returns a Parent Node. If the Node is a Root Node, returns empty.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - target Node
     * @return Parent Node or empty if the Node is a Root Node
     */
    Optional<ENTITY> getParent(ENTITY node);

    /**
     * Returns previous Sibling or empty if the target Node is a first Child.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - target Node
     * @return previous Sibling or empty if the target Node is a first Child
     */
    Optional<ENTITY> getPrevSibling(ENTITY node);

    /**
     * Returns next Sibling or empty if the target Node is a last Child.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - target Node
     * @return next Sibling or empty if the target Node is a last Child
     */
    Optional<ENTITY> getNextSibling(ENTITY node);

    /**
     * Returns a flat List of target Node's parents sorted from the deepest to the Root node, asc.
     * If the target Node is a Root Node, returns empty List.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - target Node
     * @return list of target Node's parents
     */
    List<ENTITY> getParents(ENTITY node);

    /**
     * Returns a flat list of all Node's direct and indirect Children/Sescendants sorted by the LEFT asc.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - parent Node
     * @return List of Child Nodes
     */
    List<ENTITY> getTreeAsList(ENTITY node);

    /**
     * Returns a recursive structure of all Node's direct and indirect Children/Sescendants.
     * Each level contains Nodes sorted by the LEFT asc.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be returned.
     *
     * @param node - parent Node
     * @return Recursive Tree of Child Nodes
     */
    Tree<ID, ENTITY> getTree(ENTITY node);

    /**
     * Rebuilds entire Tree based on parentId - id relationship. Useful when Tree was destroyed by an action .
     * performed outside of the Repository or if you want to initialize a new tree with Nodes previously created
     * by a batch operation outside of the Repository (like batch DB Insets).
     * All LEFT/RIGHT/LEVEL values will be properly assigned and the Tree will be sorted based on ID values.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     */
    void rebuildTree();

    /**
     * Destroys entire Tree structure by setting LEFT/RIGHT/LEVEL values of all Nodes to 0.
     * May cause SQL Exception if there are unique indexes created on the corresponding columns.
     * If Repository has a Tree Discriminator defined, only the Nodes belonging to that Tree wil be affected.
     */
    void destroyTree();

    /**
     * Inserts or Updates (depending if id is null or not) the node as a first Root Node in the Tree.
     * This is a good way to point of initializing an empty Tree.
     * If the Target Node is already a first Root, returns without any actions.
     * If Repository has a Tree Discriminator defined, Node will be only set as a first Root of that Tree.
     *
     * @param node - target Node
     */
    void insertAsFirstRoot(ENTITY node);

    /**
     * Inserts or Updates (depending if id is null or not) the node as a last Root Node in the Tree.
     * This is a good wntry point of initializing an empty Tree.
     * If the Target Node is already a last Root, returns without any actions.
     * If Repository has a Tree Discriminator defined, Node will be only set as a last Root of that Tree.
     *
     * @param node - target Node
     */
    void insertAsLastRoot(ENTITY node);

    /**
     * Lock interface that serves as a Tree/Repository locking source.
     * Contains a default implementation of NoLock class used when no locking is required.
     *
     * @param <ID> - Nested Node Identifier Class
     * @param <N>  - Nested Node Class
     */
    interface Lock<ID extends Serializable, N extends NestedSet<ID>> {

        /**
         * Lock one or more Tree Nodes based on state of the target Node.
         * Can be used to Lock entire Repository or a Tree defined by a Discriminator value held by the target Node.
         * Should always fail if the entire Repository is already locked.
         *
         * @param node - target Node
         * @return - true if lock was successfull, false if unable to lock
         */
        boolean lockNode(N node);

        /**
         * Unlock one or more tree nodes based on state of the target node.
         * Implementation should allow to unlock the same scope/amount of nodes as the corresponding lock() method.
         * Node's internal state should allow to compute the exact same Handle that served as a locking point in the lock() method.
         *
         * @param node - target Node
         */
        void unlockNode(N node);

        /**
         * Lock entire Repository with regardless of any tree discriminators defined. It will be used when performing tree rebuilding.
         * Should always take precedence before locking Nodes.
         *
         * @return - true if lock was successful, false if unable to lock
         */
        boolean lockRepository();

        /**
         * Unlocks entire Repository locked by tbe lockRepository method.
         */
        void unlockRepository();
    }


}
