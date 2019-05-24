package com.minlia.modules.rbac.menu.service;

import com.minlia.cloud.code.Code;
import com.minlia.modules.rbac.menu.bean.MenuGrantRO;
import com.minlia.modules.rbac.menu.bean.MenuQRO;
import com.minlia.modules.rbac.menu.entity.Menu;

import java.util.List;

public interface MenuService{

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    Boolean disable(Long id);

    Boolean display(Long id);

    int delete(Long id);

    Code grant(MenuGrantRO grantRO);

    List<Menu> selectByAll(MenuQRO qro);
}
