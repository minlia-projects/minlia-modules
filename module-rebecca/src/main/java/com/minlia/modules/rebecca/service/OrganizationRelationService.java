package com.minlia.modules.rebecca.service;

import com.minlia.modules.rebecca.bean.domain.Organization;
import com.minlia.modules.rebecca.bean.domain.OrganizationRelation;

import java.util.List;

public interface OrganizationRelationService {

    void insert(Organization organization);

    void update(Organization newOrganization, Organization oldOrganization);

    int deleteByPrimaryKey(Long ancestor, Long descendant);

    int insertSelective(OrganizationRelation record);

    List<Long> selectDescendantByAncestor(Long ancestor);

    int deleteByAncestor(Long ancestor);

    List<OrganizationRelation> selectAllByAncestor(Long ancestor);

    int deleteByBatchAncestors(List<Long> ancestors);

    int deleteByBatchDescendants(List<Long> descendants);
}
