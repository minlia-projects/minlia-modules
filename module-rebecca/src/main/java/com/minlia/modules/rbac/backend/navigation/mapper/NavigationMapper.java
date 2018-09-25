package com.minlia.modules.rbac.backend.navigation.mapper;


import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.backend.navigation.body.NavigationQueryRequestBody;
import com.minlia.modules.rbac.backend.navigation.entity.Navigation;
import com.minlia.modules.rbac.backend.navigation.enumeration.NavigationType;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface NavigationMapper {

    void create(Navigation navigation);

    void update(Navigation navigation);

    void delete(Long id);

    void updateType(Long id, NavigationType type);

    void grant(Long roleId, List<Long> ids);

    void display(Long id, boolean display);

    long count(NavigationQueryRequestBody body);

    Navigation queryById(Long id);

    List<Navigation> queryByParentId(Long parentId);

    List<Navigation> queryByRoleId(Long roleId);

    List<Navigation> queryList(NavigationQueryRequestBody requestBody);

    PageInfo<Navigation> queryPage(RowBounds rowBounds);

}