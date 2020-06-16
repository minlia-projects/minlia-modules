
package com.minlia.module.nestedset.delegate.query.jdbc;

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

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class JdbcKeyHolder extends GeneratedKeyHolder implements Serializable {

    @SuppressWarnings("unchecked")
    public <T> T getKeyValueAs(Class<T> keyClass) {
        if (this.getKeyList().isEmpty()) {
            return null;
        } else if (this.getKeyList().size() <= 1 && this.getKeyList().get(0).size() <= 1) {
            Iterator<Object> keyIter = ((Map)this.getKeyList().get(0)).values().iterator();
            if (keyIter.hasNext()) {
                Object key = keyIter.next();
                if (!(keyClass.isAssignableFrom(key.getClass()))) {
                    throw new DataRetrievalFailureException("The generated key is not of a supported type. Unable to cast [" + key.getClass().getName() + "] to [" + keyClass.getName() + "]");
                } else {
                    return keyClass.cast(key);
                }
            } else {
                throw new DataRetrievalFailureException("Unable to retrieve the generated key. Check that the table has an identity column enabled.");
            }
        } else {
            throw new InvalidDataAccessApiUsageException("The getKey method should only be used when a single key is returned.  The current key entry contains multiple keys: " + this.getKeyList());
        }
    }
}
