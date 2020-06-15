package com.minlia.module.nestedset.config;

public class Configuration {
    private String leftFieldName;
    private String rightFieldName;
    private String levelFieldName;
    private String rootIdFieldName;
    private String entityName;

    private boolean hasManyRoots = false;

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

    public String getRootIdFieldName() {
        return rootIdFieldName;
    }

    public void setRootIdFieldName(String rootIdFieldName) {
        this.rootIdFieldName = rootIdFieldName;
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
                + ", rootIdFieldName:" + this.rootIdFieldName
                + "]";
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String tableName) {
        this.entityName = tableName;
    }
}
