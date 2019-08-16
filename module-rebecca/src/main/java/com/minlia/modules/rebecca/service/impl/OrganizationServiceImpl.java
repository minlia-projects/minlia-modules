package com.minlia.modules.rebecca.service.impl;

import com.google.common.collect.Lists;
import com.minlia.modules.rebecca.bean.domain.Organization;
import com.minlia.modules.rebecca.bean.dto.OrganizationTree;
import com.minlia.modules.rebecca.bean.dto.TreeUtil;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import com.minlia.modules.rebecca.mapper.OrganizationMapper;
import com.minlia.modules.rebecca.service.OrganizationRelationService;
import com.minlia.modules.rebecca.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationRelationService organizationRelationService;

    @Override
    public Organization insertSelective(Organization record) {
        organizationMapper.insertSelective(record);
        organizationRelationService.insert(record);
        return record;
    }

    @Override
    public Boolean updateByPrimaryKeySelective(Organization organization) {
        Organization oldOrganization = organizationMapper.selectByPrimaryKey(organization.getId());

        //更新信息
        this.organizationMapper.updateByPrimaryKeySelective(organization);

        //更新组织关系
        organizationRelationService.update(organization, oldOrganization);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByPrimaryKey(Long id) {
        //查询除所有子组织ID、包含自己
        List<Long> idList = organizationRelationService.selectDescendantByAncestor(id);

        //删除级联关系
        organizationRelationService.deleteByBatchDescendants(idList);

        //删除组织
        organizationMapper.deleteBatchIds(idList);
        return Boolean.TRUE;
    }

    @Override
    public Organization selectByPrimaryKey(Long id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrganizationTree> selectTree() {
        return buildTree(this.organizationMapper.selectAll(), 0L);
    }

    @Override
    public List<OrganizationTree> getUserTree() {
        Long orgId = SecurityContextHolder.getUserContext().getOrgId();
        if (null == orgId) {
            return Lists.newArrayList();
        }

        List<Long> descendantIdList = organizationRelationService.selectDescendantByAncestor(orgId);

        List<Organization> deptList = organizationMapper.selectBatchIds(descendantIdList);
        return buildTree(deptList, orgId);
    }

    private List<OrganizationTree> buildTree(List<Organization> organizations, long root) {
        List<OrganizationTree> trees = organizations.stream()
                .filter(organization -> !organization.getId().equals(organization.getParentId()))
                .map(organization -> {
                    OrganizationTree tree = new OrganizationTree();
                    tree.setId(organization.getId());
                    tree.setName(organization.getName());
                    tree.setParentId(organization.getParentId());
                    tree.setSort(organization.getSort());
                    tree.setDisFlag(organization.getDisFlag());
                    return tree;
                }).collect(Collectors.toList());
        return TreeUtil.build(trees, root);
    }

}
