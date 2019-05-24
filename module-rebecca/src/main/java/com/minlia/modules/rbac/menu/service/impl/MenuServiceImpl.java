package com.minlia.modules.rbac.menu.service.impl;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rbac.constant.RoleCode;
import com.minlia.modules.rbac.enumeration.NavigationType;
import com.minlia.modules.rbac.menu.bean.MenuGrantRO;
import com.minlia.modules.rbac.menu.bean.MenuQRO;
import com.minlia.modules.rbac.menu.constant.MenuCode;
import com.minlia.modules.rbac.menu.entity.Menu;
import com.minlia.modules.rbac.menu.mapper.MenuMapper;
import com.minlia.modules.rbac.menu.service.MenuService;
import com.minlia.modules.rbac.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public int insertSelective(Menu menu) {
        if (null != menu.getParentId()) {
            Menu parent = menuMapper.selectByPrimaryKey(menu.getParentId());
            ApiAssert.notNull(parent, SystemCode.Message.DATA_NOT_EXISTS);
            if (parent.getType().equals(NavigationType.LEAF)) {
                parent.setType(NavigationType.FOLDER);
                menuMapper.updateByPrimaryKeySelective(parent);
            }
        }
        return menuMapper.insertSelective(menu);
    }

    @Override
    public Menu selectByPrimaryKey(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu record) {
        return menuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Boolean disable(Long id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        ApiAssert.notNull(menu, MenuCode.Message.NOT_EXISTS);
        menu.setDisFlag(!menu.getDisFlag());
        menuMapper.updateByPrimaryKey(menu);
        return menu.getDisFlag();
    }

    @Override
    public Boolean display(Long id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        ApiAssert.notNull(menu, MenuCode.Message.NOT_EXISTS);
        menu.setHideFlag(!menu.getHideFlag());
        menuMapper.updateByPrimaryKey(menu);
        return menu.getHideFlag();
    }

    @Override
    public int delete(Long id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        ApiAssert.notNull(menu, MenuCode.Message.NOT_EXISTS);

        //当有子节点时报出异常
        ApiAssert.state(NavigationType.LEAF.equals(menu.getType()), MenuCode.Message.CAN_NOT_DELETE_HAS_CHILDREN);

        //检查父节点是否还有子节点, 如果无子节点时更新父节点为LEAF类型
        if (null != menu.getParentId()) {
            long countChildren = menuMapper.countByParentId(menu.getParentId());
            //如果只有一个儿子
            if (countChildren == 1) {
                menuMapper.updateTypeById(NavigationType.LEAF.name(), menu.getParentId());
            }
        }
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Code grant(MenuGrantRO grantRO) {
        boolean existsRole = roleService.exists(grantRO.getRoleId());
        ApiAssert.state(existsRole, RoleCode.Message.NOT_EXISTS);

        if (CollectionUtils.isNotEmpty(grantRO.getIds())) {
            for (Long id: grantRO.getIds()) {
                boolean exists = menuMapper.countById(id) > 0;
                ApiAssert.state(exists, MenuCode.Message.NOT_EXISTS);
            }
            menuMapper.grant(grantRO.getRoleId(), grantRO.getIds());
        } else {
            menuMapper.clearByRoleId(grantRO.getRoleId());
        }
        return SystemCode.Message.SUCCESS;
    }

    @Override
    public List<Menu> selectByAll(MenuQRO qro) {
        List<Menu> list = menuMapper.selectByAll(qro);
        bindChirdren(list, qro.getRoleId());
        return list;
    }

    private void bindChirdren(List<Menu> menus, Long roleId){
        if (CollectionUtils.isNotEmpty(menus)) {
            for (Menu menu : menus) {
                if (NavigationType.FOLDER.equals(menu.getType())) {
                    List<Menu> chirdren = menuMapper.selectByAll(MenuQRO.builder().parentId(menu.getId()).hideFlag(false).roleId(roleId).build());
                    menu.setChildren(chirdren);
                    bindChirdren(chirdren, roleId);
                }
            }
        }
    }

}
