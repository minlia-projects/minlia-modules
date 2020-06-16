package com.minlia.module.nestedset.config;

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

public class Configuration {
    private String leftFieldName;
    private String rightFieldName;
    private String levelFieldName;
    private String parentIdFieldName;
    private String idFieldName;
    private String entityName;

    private boolean hasManyRoots = false;


    public String getIdFieldName() {
        return idFieldName;
    }

    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public String getLeftFieldName() {
        return leftFieldName;
    }

    public void setLeftFieldName(String leftFieldName) {
        this.leftFieldName = leftFieldName;
    }

    public String getRightFieldName() {
        return rightFieldName;
    }

    public void setRightFieldName(String rightFieldName) {
        this.rightFieldName = rightFieldName;
    }

    public String getLevelFieldName() {
        return levelFieldName;
    }

    public void setLevelFieldName(String levelFieldName) {
        this.levelFieldName = levelFieldName;
    }

    public String getParentIdFieldName() {
        return parentIdFieldName;
    }

    public void setParentIdFieldName(String parentIdFieldName) {
        this.parentIdFieldName = parentIdFieldName;
        this.hasManyRoots = true;
    }

    public boolean hasManyRoots() {
        return this.hasManyRoots;
    }

    @Override
    public String toString() {
        return "[leftFieldName: " + this.leftFieldName
                + ", rightFieldName:" + this.rightFieldName
                + ", levelFieldName: " + this.levelFieldName
                + ", rootIdFieldName:" + this.parentIdFieldName
                + ", idFieldName:" + this.idFieldName
                + "]";
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String tableName) {
        this.entityName = tableName;
    }
}
