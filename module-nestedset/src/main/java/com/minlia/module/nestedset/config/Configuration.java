package com.minlia.module.nestedset.config;

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
