package com.minlia.modules.rebecca.service.impl;

import com.minlia.modules.rebecca.bean.domain.Organization;
import com.minlia.modules.rebecca.bean.dto.OrganizationTree;

import java.util.List;

public interface OrganizationService {

    Boolean insertSelective(Organization record);

    Boolean updateByPrimaryKeySelective(Organization record);

    Boolean deleteByPrimaryKey(Long id);

    Organization selectByPrimaryKey(Long id);

    /**
     * 查询组织树菜单
     *
     * @return
     */
    List<OrganizationTree> selectTree();

    /**
     * 查询用户组织树菜单
     *
     * @return
     */
    List<OrganizationTree> getUserTree();

}
