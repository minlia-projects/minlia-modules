
package com.minlia.module.nestedset.config.jdbc.discriminator;

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

import java.util.List;

/**
 * Tree Discriminator for use with JDBC Repository implementation.
 * Allows to store multiple intependant Trees in one Repository/Collection.
 */
public interface JdbcTreeDiscriminator {

    /**
     * Returns String with SQL query part that narrows the scope of all SQL Queries used by JDBC implementation
     *
     * @return - String with SQL query part that narrows the scope of all SQL Queries used by JDBC implementation.
     */
    String getQueryPart();

    /**
     * Returns List of values that should be set in the Prepared Statement and that correspond to the parameters used in the getQueryPart() method.
     *
     * @return - List of values that should be set in the Prepared Statement and that correspond to the parameters used in the getQueryPart() method.
     */
    List<Object> getParameters();
}
