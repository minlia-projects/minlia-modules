package com.minlia.modules.rebecca.service;

import cn.hutool.core.collection.CollUtil;
import com.minlia.modules.rebecca.bean.domain.Organization;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.minlia.modules.rebecca.mapper.OrganizationRelationMapper;
import com.minlia.modules.rebecca.bean.domain.OrganizationRelation;
import com.minlia.modules.rebecca.service.impl.OrganizationRelationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationRelationServiceImpl implements OrganizationRelationService {

    @Resource
    private OrganizationRelationMapper organizationRelationMapper;

    @Override
    public void insert(Organization organization) {
        //查询所有爷爷
        List<OrganizationRelation> relationList = this.organizationRelationMapper
                .selectByAll(OrganizationRelation.builder().descendant(organization.getParentId()).build())
                .stream().map(relation -> {
                    relation.setDescendant(organization.getId());
                    return relation;
                }).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(relationList)) {
            //维护爷爷与自己关系
//            this.saveBatch(relationList);
            relationList.stream().forEach(relation -> organizationRelationMapper.insertSelective(relation));
        }

        //自己也要维护到关系表中
        OrganizationRelation own = new OrganizationRelation();
        own.setDescendant(organization.getId());
        own.setAncestor(organization.getId());
        this.organizationRelationMapper.insertSelective(own);
    }

    @Override
    public void update(Organization newOrganization, Organization oldOrganization) {
        //如果父组织有变动，重新维护关系:  partnerId都为空、都不为空切partnerId相等时不做处理
        if (null == newOrganization.getParentId() && null == oldOrganization.getParentId()) {
            return;
        }

        if (null != newOrganization.getParentId() && null != oldOrganization.getParentId() && newOrganization.getParentId().equals(oldOrganization.getParentId())) {
            return;
        }

        //所有老祖宗
        List<OrganizationRelation> olbAncestorRelationList = organizationRelationMapper.selectAllByDescendant(oldOrganization.getId());

        //所有后代
        List<OrganizationRelation> descendantRelationList = organizationRelationMapper.selectAllByAncestor(oldOrganization.getId());

        //移除老祖宗和后代的关系
        olbAncestorRelationList.stream().forEach(ancestorRelation -> {
            //如果祖宗是自己，跳过
            if (!ancestorRelation.getAncestor().equals(newOrganization.getId())) {
                descendantRelationList.stream().forEach(descendantRelation -> {
                    this.organizationRelationMapper.deleteByPrimaryKey(ancestorRelation.getAncestor(), descendantRelation.getDescendant());
                });
            }
        });

        //所有新祖宗
        List<OrganizationRelation> newAncestorRelationList = organizationRelationMapper.selectAllByDescendant(newOrganization.getParentId());

        //维护新祖宗与后代的关系
        newAncestorRelationList.stream().forEach(ancestorRelation -> {
            descendantRelationList.stream().forEach(descendantRelation -> {
                this.organizationRelationMapper.insertSelective(OrganizationRelation.builder().ancestor(ancestorRelation.getAncestor()).descendant(descendantRelation.getDescendant()).build());
            });
        });
    }

    @Override
    public int deleteByPrimaryKey(Long ancestor, Long descendant) {
        return organizationRelationMapper.deleteByPrimaryKey(ancestor, descendant);
    }

    @Override
    public int insertSelective(OrganizationRelation record) {
        return organizationRelationMapper.insertSelective(record);
    }

    @Override
    public List<Long> selectDescendantByAncestor(Long ancestor) {
        return organizationRelationMapper.selectDescendantByAncestor(ancestor);
    }

    @Override
    public int deleteByAncestor(Long ancestor) {
        return organizationRelationMapper.deleteByAncestor(ancestor);
    }

    @Override
    public List<OrganizationRelation> selectAllByAncestor(Long ancestor) {
        return organizationRelationMapper.selectAllByAncestor(ancestor);
    }

    @Override
    public int deleteByBatchAncestors(List<Long> ancestors) {
        return organizationRelationMapper.deleteByBatchAncestors(ancestors);
    }

    @Override
    public int deleteByBatchDescendants(List<Long> descendants) {
        return organizationRelationMapper.deleteByBatchDescendants(descendants);
    }
}
