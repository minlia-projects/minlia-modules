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
package com.minlia.module.nestedset.model;

import java.io.Serializable;

/**
 * Base Interface that has to be implemented by any Entity/Object to be used by NestedNodeRepository.
 * <p>
 * It is strongly recommended to follow Java-bean naming standards. It is important especially for JPA implementation.
 * JPA Criteria Queries are constructed using the constants defined in this interface. In order to work correctly
 * you have to follow this rule. It is not required by other implementations, but still it is recommended.
 * <p>
 * Although Nested Set model doesn't require the presence of PARENT_ID mapping, it is required.
 * Nested Set Model is a very fragile construct. One manual upadte can destroy the tree beyond repair.
 * we uses PARENT_ID mapping to strengthen the Tree Structure and to be able to repair the Nested Set Model if needed.
 *
 * @param <ID> - Nested Node Identifier Class
 */
public interface NestedSet<ID extends Serializable> {

    /**
     * Standardized Field Names used internally.
     * Any Java class that implements the NestedNode interface should have fields with names matching the below constants.
     */
    String LEFT = "treeLeft";
    String RIGHT = "treeRight";
    String LEVEL = "treeLevel";
    String PARENT_ID = "parentId";
    String ID = "id";

    /**
     * Should be PK if using Database.
     *
     * @return unique Identifier of the Entity/Object.
     */
    ID getId();

    /**
     * @return Nested Set Model LEFT value
     */
    Long getLeft();

    /**
     * @return Nested Set Model RIGHT value
     */
    Long getRight();

    /**
     * @return Nested Set Model LEVEL value
     */
    Long getLevel();

    /**
     * Should be a FK if using Database.
     *
     * @return Parent Idendifier
     */
    ID getParentId();

    /**
     * Sets the unique Identifier of the Entity/Object.
     *
     * @param id - unique Identifier of the Entity/Object.
     */
    void setId(ID id);

    /**
     * Sets the Nested Set Model LEFT value
     *
     * @param left - Nested Set Model LEFT value
     */
    void setLeft(Long left);

    /**
     * Sets the Nested Set Model RIGHT value
     *
     * @param right - Nested Set Model RIGHT value
     */
    void setRight(Long right);

    /**
     * Sets the Nested Set Model LEVEL value
     *
     * @param level - Nested Set Model LEVEL value
     */
    void setLevel(Long level);

    /**
     * Sets the Parent Idendifier
     *
     * @param parent - Parent Idendifier
     */
    void setParentId(ID parent);

}
