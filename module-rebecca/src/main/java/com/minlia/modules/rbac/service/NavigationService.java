package com.minlia.modules.rbac.service;


import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.modules.rbac.bean.to.NavigationCTO;
import com.minlia.modules.rbac.bean.to.NavigationGrantTO;
import com.minlia.modules.rbac.bean.qo.NavigationQO;
import com.minlia.modules.rbac.bean.to.NavigationUTO;
import com.minlia.modules.rbac.bean.domain.Navigation;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 导航接口
 * Created by will on 6/17/17.
 */
public interface NavigationService {

    Response create(NavigationCTO requestBody);

    Navigation update(NavigationUTO requestBody);

    void delete(Long id);

    void grant(NavigationGrantTO requestBody);

    Boolean display(Long id);

    Navigation queryById(Long id);

    List<Navigation> queryByParentId(Long parentId);

    List<Navigation> queryByRoleId(Long id);

    List<Navigation> queryList(NavigationQO requestBody);

    PageInfo<Navigation> queryPage(NavigationQO requestBody, Pageable pageable);

}
