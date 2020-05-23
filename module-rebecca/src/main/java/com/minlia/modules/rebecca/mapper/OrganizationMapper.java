package com.minlia.modules.rebecca.mapper;

import com.minlia.module.data.datascopee.DataScope;
import com.minlia.modules.rebecca.bean.domain.Organization;

import java.util.List;

public interface OrganizationMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(Organization record);

    @DataScope(name = "id")
    Organization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Organization record);

    @DataScope(name = "id")
    List<Organization> selectAll();

    @DataScope(name = "id")
    List<Organization> selectBatchIds(List<Long> idList);

    int deleteBatchIds(List<Long> idList);

}