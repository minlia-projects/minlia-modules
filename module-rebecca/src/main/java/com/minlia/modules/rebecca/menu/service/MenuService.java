package com.minlia.modules.rebecca.menu.service;

import com.minlia.cloud.code.Code;
import com.minlia.modules.rebecca.menu.bean.MenuGrantRO;
import com.minlia.modules.rebecca.menu.bean.MenuQRO;
import com.minlia.modules.rebecca.menu.entity.Menu;

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
