package com.minlia.modules.rbac.mapper;


import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.bean.qo.NavigationQO;
import com.minlia.modules.rbac.bean.domain.Navigation;
import com.minlia.modules.rbac.enumeration.NavigationType;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface NavigationMapper {

    void create(Navigation navigation);

    void update(Navigation navigation);

    void delete(Long id);

    void updateType(Long id, NavigationType type);

    void grant(Long roleId, List<Long> ids);

    void display(Long id, boolean display);

    long count(NavigationQO body);

    Navigation queryById(Long id);

    List<Navigation> queryByRoleId(Long roleId);

    List<Navigation> queryList(NavigationQO qo);

    PageInfo<Navigation> queryPage(RowBounds rowBounds);

    void clear(Long roleId);
}