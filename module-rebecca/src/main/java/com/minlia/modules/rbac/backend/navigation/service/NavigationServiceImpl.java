package com.minlia.modules.rbac.backend.navigation.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.backend.common.constant.SecurityApiCode;
import com.minlia.modules.rbac.backend.navigation.body.NavigationCreateRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationGrantRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationQueryRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationUpdateRequestBody;
import com.minlia.modules.rbac.backend.navigation.entity.Navigation;
import com.minlia.modules.rbac.backend.navigation.enumeration.NavigationType;
import com.minlia.modules.rbac.backend.navigation.mapper.NavigationMapper;
import com.minlia.modules.rbac.backend.role.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class NavigationServiceImpl implements NavigationService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private NavigationMapper navigationMapper;

    @Override
    public StatefulBody create(NavigationCreateRequestBody body) {
        boolean exists = navigationMapper.count(NavigationQueryRequestBody.builder().parentId(body.getParentId()).name(body.getName()).build()) > 0;
        if (!exists) {
            updateTypeByChildren(body.getParentId());

            Navigation navigation = Navigation.builder().parentId(body.getParentId()).name(body.getName()).icon(body.getIcon()).state(body.getState()).orders(body.getOrders()).type(NavigationType.LEAF).build();
            navigationMapper.create(navigation);
            return SuccessResponseBody.builder().payload(navigation).build();
        } else {
            return FailureResponseBody.builder().message("导航已存在").build();
        }
    }

    @Override
    public Navigation update(NavigationUpdateRequestBody body) {
        Navigation navigation = navigationMapper.queryById(body.getId());
        ApiPreconditions.is(null == navigation, SecurityApiCode.NAVIGATION_NOT_EXISTS,"导航不存在");

        updateTypeByChildren(body.getParentId());

        mapper.map(body,navigation);
        navigationMapper.update(navigation);
        return navigation;
    }

    /**
     * 更新老子类型
     * @param parentId
     */
    private void updateTypeByChildren(Long parentId) {
        if (null != parentId) {
            Navigation parent = navigationMapper.queryById(parentId);
            ApiPreconditions.is(null == parent, SecurityApiCode.NAVIGATION_PARENT_NOT_EXISTS,"父导航不存在");

            //当创建子项时设置父类为FOLDER
            if (NavigationType.LEAF.equals(parent.getType())) {
                navigationMapper.updateType(parent.getId(),NavigationType.FOLDER);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Navigation navigation = navigationMapper.queryById(id);
        ApiPreconditions.is(null == navigation, SecurityApiCode.NAVIGATION_NOT_EXISTS,"导航不存在");

        //当有子节点时报出异常
        ApiPreconditions.is(NavigationType.FOLDER.equals(navigation.getType()),SecurityApiCode.NAVIGATION_CAN_NOT_DELETE_HAS_CHILDREN,"有子菜单，不能删除");

        //检查父节点是否还有子节点, 如果无子节点时更新父节点为LEAF类型
        if (null != navigation.getParentId()) {
            long countChildren = navigationMapper.count(NavigationQueryRequestBody.builder().parentId(navigation.getParentId()).build());
            //如果只有一个儿子
            if (countChildren == 1) {
                navigationMapper.updateType(navigation.getParentId(),NavigationType.LEAF);
            }
        }
        navigationMapper.delete(id);
    }

    @Override
    public void grant(NavigationGrantRequestBody body) {
        boolean existsRole = roleService.exists(body.getRoleId());
        ApiPreconditions.not(existsRole,SecurityApiCode.ROLE_NOT_EXISTED,"角色不存在");

        if (CollectionUtils.isNotEmpty(body.getIds())) {
            for (Long id: body.getIds()) {
                boolean exists = navigationMapper.count(NavigationQueryRequestBody.builder().id(id).display(true).isOneLevel(false).build()) > 0;
                ApiPreconditions.not(exists,SecurityApiCode.NAVIGATION_NOT_EXISTS,"导航不存在");
            }
        }
        navigationMapper.grant(body.getRoleId(),body.getIds());
    }

    @Override
    public Boolean display(Long id) {
        Navigation navigation = navigationMapper.queryById(id);
        ApiPreconditions.is(null == navigation, SecurityApiCode.NAVIGATION_NOT_EXISTS,"导航不存在");
        navigationMapper.display(id,!navigation.getDisplay());
        return !navigation.getDisplay();
    }

    @Override
    public Navigation queryById(Long id) {
        return navigationMapper.queryById(id);
    }

    @Override
    public List<Navigation> queryByParentId(Long parentId) {
        return navigationMapper.queryByParentId(parentId);
    }

    @Override
    public List<Navigation> queryByRoleId(Long id) {
       return this.queryList(NavigationQueryRequestBody.builder().roleId(id).isOneLevel(true).build());
    }

    @Override
    public List<Navigation> queryList(NavigationQueryRequestBody requestBody) {
        List<Navigation> navigations = navigationMapper.queryList(requestBody);
        bindChirdren(navigations);
        return navigations;
    }

    @Override
    public PageInfo<Navigation> queryPage(RowBounds rowBounds) {
        return navigationMapper.queryPage(rowBounds);
    }

    private void bindChirdren(List<Navigation> navigations){
        if (CollectionUtils.isNotEmpty(navigations)) {
            for (Navigation navigation : navigations) {
                if (NavigationType.FOLDER.equals(navigation.getType())) {
                    List<Navigation> chirdren = navigationMapper.queryList(NavigationQueryRequestBody.builder().parentId(navigation.getId()).display(true).build());
                    navigation.setChildren(chirdren);
                    bindChirdren(chirdren);
                }
            }
        }
    }

}
