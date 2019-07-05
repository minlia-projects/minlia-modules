package com.minlia.modules.rebecca.mapper;


import com.minlia.modules.rebecca.bean.domain.Navigation;
import com.minlia.modules.rebecca.bean.qo.NavigationQO;
import com.minlia.modules.rebecca.bean.vo.MyNavigationVO;
import com.minlia.modules.rebecca.enumeration.NavigationType;

import java.util.List;

public interface NavigationMapper {

    int create(Navigation navigation);

    int update(Navigation navigation);

    int delete(Long id);

    int deleteMappingById(Long id);

    int updateType(Long id, NavigationType type);

    int grant(Long roleId, List<Long> ids);

    void display(Long id, boolean display);

    long count(NavigationQO qro);

    Navigation queryById(Long id);

    List<Navigation> queryList(NavigationQO qro);

    List<MyNavigationVO> queryMyNavigationList(NavigationQO qro);

    int clear(Long roleId);

}