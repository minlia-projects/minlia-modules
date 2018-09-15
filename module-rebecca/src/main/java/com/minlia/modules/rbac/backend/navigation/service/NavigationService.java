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

    /**
     * 授权：给角色分配导航
     * @param requestBody
     */
    void grant(NavigationGrantRequestBody requestBody);

    /**
     * 展示、隐藏
     * @param id
     */
    Boolean display(Long id);

    /**
     * 根据ID查询：包含儿子
     * @param id
     * @return
     */
    Navigation queryById(Long id);

    /**
     * 根据老子查询：包含儿子
     * @param parentId
     * @return
     */
    List<Navigation> queryByParentId(Long parentId);

    List<Navigation> queryByRoleId(Long id);

    /**
     *
     * @param requestBody
     * @return
     */
    List<Navigation> queryList(NavigationQueryRequestBody requestBody);

    /**
     * 分页查询：包含儿子
     * @param requestBody
     * @param pageable
     * @return
     */
    PageInfo<Navigation> queryPage(NavigationQueryRequestBody requestBody, Pageable pageable);

}
