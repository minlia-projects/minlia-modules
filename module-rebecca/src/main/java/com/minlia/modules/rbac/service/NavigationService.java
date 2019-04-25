package com.minlia.modules.rbac.service;


import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.modules.rbac.bean.domain.Navigation;
import com.minlia.modules.rbac.bean.qo.NavigationQO;
import com.minlia.modules.rbac.bean.to.NavigationCTO;
import com.minlia.modules.rbac.bean.to.NavigationGrantTO;
import com.minlia.modules.rbac.bean.to.NavigationUTO;
import com.minlia.modules.rbac.bean.vo.MyNavigationVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 导航接口
 * Created by will on 6/17/17.
 */
public interface NavigationService {

    Response create(NavigationCTO cro);

    Navigation update(NavigationUTO uro);

    void delete(Long id);

    void grant(NavigationGrantTO grantTO);

    Boolean display(Long id);

    Navigation queryById(Long id);

    List<Navigation> queryList(NavigationQO qro);

    PageInfo<Navigation> queryPage(NavigationQO qro, Pageable pageable);

    List<MyNavigationVO> queryMyNavigationList(NavigationQO qro);

}
