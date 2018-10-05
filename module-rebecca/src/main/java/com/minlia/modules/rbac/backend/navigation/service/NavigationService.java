package com.minlia.modules.rbac.backend.navigation.service;


import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.modules.rbac.backend.navigation.body.NavigationCreateRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationGrantRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationQueryRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationUpdateRequestBody;
import com.minlia.modules.rbac.backend.navigation.entity.Navigation;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 导航接口
 * Created by will on 6/17/17.
 */
public interface NavigationService {

    Response create(NavigationCreateRequestBody requestBody);

    Navigation update(NavigationUpdateRequestBody requestBody);

    void delete(Long id);

    void grant(NavigationGrantRequestBody requestBody);

    Boolean display(Long id);

    Navigation queryById(Long id);

    List<Navigation> queryByParentId(Long parentId);

    List<Navigation> queryByRoleId(Long id);

    List<Navigation> queryList(NavigationQueryRequestBody requestBody);

    PageInfo<Navigation> queryPage(NavigationQueryRequestBody requestBody, Pageable pageable);

}
