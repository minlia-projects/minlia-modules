package com.minlia.modules.rbac.backend.navigation.mapper;


import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.backend.navigation.body.NavigationQueryRequestBody;
import com.minlia.modules.rbac.backend.navigation.entity.Navigation;
import com.minlia.modules.rbac.backend.navigation.enumeration.NavigationType;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface NavigationMapper {

    void create(Navigation role);

    void update(Navigation role);

    void delete(Long id);

    void updateType(Long id, NavigationType type);

    void grant(Long roleId, List<Long> ids);

    Navigation queryById(Long id);

    long count(NavigationQueryRequestBody body);

    List<Navigation> queryByParentId(Long parentId);

    PageInfo<Navigation> queryPage(RowBounds rowBounds);

}