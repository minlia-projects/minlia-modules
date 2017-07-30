package com.minlia.cloud.data.batis.support.persistence.entity;

import com.minlia.cloud.annotation.SearchField;
import com.minlia.cloud.body.query.QueryOperator;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.MappedSuperclass;

@MappedSuperclass
@javax.persistence.MappedSuperclass
public abstract class AbstractTreeEntity<ENTITY extends AbstractTreeEntity> extends AbstractEntity {

    public static final String ROOT = "1";
    public static final String F_NAME = "name";
    public static final String F_PARENTID = "parentId";
    public static final String F_PARENTIDS = "parentIds";
    public static final String F_ISLEAF = "isLeaf";
    public static final String F_SORT = "sort";
    public static final String F_PARENT = "parent";
    private static final long serialVersionUID = 1L;


    /*** 组织名称 */
    @SearchField(op = QueryOperator.like)
    @Column(name = "name")
    @javax.persistence.Column
    protected String name;

    /*** 上级组织 */
    @SearchField
    @Column(name = "parent_id")
    @javax.persistence.Column
    protected Long parentId;

    /*** 所有父编号 */
    @SearchField(op = QueryOperator.like)
    @javax.persistence.Column
    @Column(name = "parent_ids")
    protected String parentIds;

    /*** 上级组织 */
//    @ManyToOne
//    @JoinColumn(name = "parent_id")

//    @javax.persistence.ManyToOne
//    @javax.persistence.JoinColumn(name = "parent_id")
//    @javax.persistence.Column(name = "parent_id")

    @javax.persistence.Transient
    @org.springframework.data.annotation.Transient
    protected ENTITY parent;

    /*** 序号 */
    @Column(name = "sort")
    @javax.persistence.Column
    protected Integer sort = 30;

    /*** 1 叶子节点 0非叶子节点 */
    @Column(name = "is_leaf")
    @javax.persistence.Column
    private boolean isLeaf = false;

    public AbstractTreeEntity() {
        super();
        this.sort = 30;
    }

    public ENTITY getParent() {
        return parent;
    }

    public void setParent(ENTITY parent) {
        this.parent = parent;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

//    public String getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(String parentId) {
//        this.parentId = parentId;
//    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    protected boolean getIsLeaf() {
        return isLeaf;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
