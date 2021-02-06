package com.minlia.module.rebecca.permission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.rebecca.permission.entity.SysPermissionEntity;
import com.minlia.module.rebecca.permission.mapper.SysPermissionMapper;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import com.minlia.module.rebecca.role.entity.SysRolePermissionEntity;
import com.minlia.module.rebecca.role.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限点 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public Boolean clear() {
        //清理关系表
        this.getBaseMapper().clear();
        return true;
    }

    @Override
    public List<Map<String, Object>> tree() {
        List<Map<String, Object>> ones = Lists.newArrayList();
        List<String> oneLevel = this.getBaseMapper().oneLevel();

        for (String one : oneLevel) {
            List<Map<String, Object>> twos = Lists.newArrayList();
            List<String> twoLevel = this.getBaseMapper().twoLevel(one);
            for (String two : twoLevel) {
                List<Map<String, Object>> threes = Lists.newArrayList();
                List<Map> threeLevel = this.getBaseMapper().threeLevel(one + SymbolConstants.DOT + two + SymbolConstants.DOT);
                for (Map three : threeLevel) {
                    threes.add(three);
                }
                Map map = new HashMap();
                map.put("name", two);
                map.put("children", threes);
                twos.add(map);
            }
            Map map = new HashMap();
            map.put("name", one);
            map.put("children", twos);
            ones.add(map);
        }
        return ones;
    }

    @Override
    public List<String> getCodesByRoleId(Long roleId) {
        return this.getBaseMapper().selectCodesByRoleId(roleId);
    }

    @Override
    public List<String> getCodesByRoleId(List<Long> roleIds) {
        return this.getBaseMapper().selectCodesByRoleIds(roleIds);
    }

    @Override
    public List<Long> getIdsByRoleId(Long roleId) {
        List<SysRolePermissionEntity> entities = sysRolePermissionService.list(Wrappers.<SysRolePermissionEntity>lambdaQuery().eq(SysRolePermissionEntity::getRoleId, roleId));
        return entities.stream().map(entity -> entity.getPermissionId()).collect(Collectors.toList());
    }

    @Override
    public List<Long> getIdsByUserId(Long userId) {
        return this.getBaseMapper().selectIdsByRoleId(userId);
    }

    @Override
    public List<Long> getIdsByRoleCode(String roleCode) {
        return this.getBaseMapper().selectIdsByRoleCode(roleCode);
    }

    @Override
    public List<Long> getIdsByUserCode(String userCode) {
        return this.getBaseMapper().selectIdsByRoleCode(userCode);
    }

    @Override
    public Set<Long> getAllIds() {
        List<SysPermissionEntity> list = this.list(Wrappers.<SysPermissionEntity>lambdaQuery().eq(SysPermissionEntity::getDisFlag, false));
        return list.stream().map(entity -> entity.getId()).collect(Collectors.toSet());
    }

}
