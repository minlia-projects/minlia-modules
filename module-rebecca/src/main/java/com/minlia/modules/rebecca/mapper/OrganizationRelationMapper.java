package com.minlia.modules.rebecca.mapper;

import java.util.List;

import com.minlia.modules.rebecca.bean.domain.OrganizationRelation;
import org.apache.ibatis.annotations.Param;

public interface OrganizationRelationMapper {

    int deleteByPrimaryKey(@Param("ancestor") Long ancestor, @Param("descendant") Long descendant);

    int insertSelective(OrganizationRelation record);

    List<OrganizationRelation> selectByAll(OrganizationRelation organizationRelation);

    int deleteByDescendant(@Param("descendant") Long descendant);

    List<Long> selectDescendantByAncestor(@Param("ancestor") Long ancestor);

    List<OrganizationRelation> selectAllByAncestor(@Param("ancestor") Long ancestor);

    List<OrganizationRelation> selectAllByDescendant(@Param("descendant") Long descendant);


    int deleteByAncestor(@Param("ancestor") Long ancestor);


    int deleteByBatchAncestors(@Param("ancestors") List<Long> ancestors);

    int deleteByBatchDescendants(@Param("descendants") List<Long> descendants);

}