package com.minlia.modules.rbac.backend.navigation.service;


import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationCreateRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationGrantRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationQueryRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationUpdateRequestBody;
import com.minlia.modules.rbac.backend.navigation.entity.Navigation;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 导航接口
 * Created by will on 6/17/17.
 */
public interface NavigationService {

    StatefulBody create(NavigationCreateRequestBody body);

    Navigation update(NavigationUpdateRequestBody body);

    void delete(Long id);

    /**
     * 授权：给角色分配导航
     * @param body
     */
    void grant(NavigationGrantRequestBody body);

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

    /**
     *
     * @param requestBody
     * @return
     */
    List<Navigation> queryList(NavigationQueryRequestBody requestBody);

    /**
     * 分页查询：包含儿子
     * @param rowBounds
     * @return
     */
    PageInfo<Navigation> queryPage(RowBounds rowBounds);
}
