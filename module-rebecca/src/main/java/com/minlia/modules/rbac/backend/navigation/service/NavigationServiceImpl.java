package com.minlia.modules.rbac.backend.navigation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
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
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public Response create(NavigationCreateRequestBody requestBody) {
        boolean exists = navigationMapper.count(NavigationQueryRequestBody.builder().parentId(requestBody.getParentId()).name(requestBody.getName()).build()) > 0;
        if (!exists) {
            updateTypeByChildren(requestBody.getParentId());

            Navigation navigation = Navigation.builder().parentId(requestBody.getParentId()).name(requestBody.getName()).icon(requestBody.getIcon()).state(requestBody.getState()).orders(requestBody.getOrders()).type(NavigationType.LEAF).display(true).build();
            navigationMapper.create(navigation);
            return Response.success(SystemCode.Message.SUCCESS,navigation);
        } else {
            return Response.failure(RebaccaCode.Message.NAVIGATION_ALREADY_EXISTS);
        }
    }

    @Override
    public Navigation update(NavigationUpdateRequestBody requestBody) {
        Navigation navigation = navigationMapper.queryById(requestBody.getId());
        ApiAssert.notNull(navigation, RebaccaCode.Message.NAVIGATION_NOT_EXISTS);

        updateTypeByChildren(requestBody.getParentId());

        mapper.map(requestBody,navigation);
        navigationMapper.update(navigation);
        return navigation;
    }

    /**
     * 更新父导航
     * @param parentId
     */
    private void updateTypeByChildren(Long parentId) {
        if (null != parentId) {
            Navigation parent = navigationMapper.queryById(parentId);
            ApiAssert.notNull(parent, RebaccaCode.Message.NAVIGATION_PARENT_NOT_EXISTS);

            //当创建子项时设置父类为FOLDER
            if (NavigationType.LEAF.equals(parent.getType())) {
                navigationMapper.updateType(parent.getId(),NavigationType.FOLDER);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Navigation navigation = navigationMapper.queryById(id);
        ApiAssert.notNull(navigation, RebaccaCode.Message.NAVIGATION_NOT_EXISTS);

        //当有子节点时报出异常
        ApiAssert.state(NavigationType.FOLDER.equals(navigation.getType()), RebaccaCode.Message.NAVIGATION_CAN_NOT_DELETE_HAS_CHILDREN);

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
    public void grant(NavigationGrantRequestBody requestBody) {
        boolean existsRole = roleService.exists(requestBody.getRoleId());
        ApiAssert.state(existsRole,RebaccaCode.Message.ROLE_NOT_EXISTED);

        if (CollectionUtils.isNotEmpty(requestBody.getIds())) {
            for (Long id: requestBody.getIds()) {
                boolean exists = navigationMapper.count(NavigationQueryRequestBody.builder().id(id).display(true).build()) > 0;
                ApiAssert.state(exists,RebaccaCode.Message.NAVIGATION_NOT_EXISTS);
            }
            navigationMapper.grant(requestBody.getRoleId(),requestBody.getIds());
        } else {
            navigationMapper.clear(requestBody.getRoleId());
        }
    }

    @Override
    public Boolean display(Long id) {
        Navigation navigation = navigationMapper.queryById(id);
        ApiAssert.notNull(navigation, RebaccaCode.Message.NAVIGATION_NOT_EXISTS);
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
        List<Navigation> navigations = navigationMapper.queryByRoleId(id);
        bindChirdren(navigations, id);
        return navigations;
    }

    @Override
    public List<Navigation> queryList(NavigationQueryRequestBody requestBody) {
        List<Navigation> navigations = navigationMapper.queryList(requestBody);
        bindChirdren(navigations, requestBody.getRoleId());
        return navigations;
    }

    @Override
    public PageInfo<Navigation> queryPage(NavigationQueryRequestBody requestBody, Pageable pageable) {
        PageInfo pageInfo = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> navigationMapper.queryList(requestBody));
        bindChirdren(pageInfo.getList(), requestBody.getRoleId());
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> navigationMapper.queryList(requestBody));
    }

    private void bindChirdren(List<Navigation> navigations, Long roleId){
        if (CollectionUtils.isNotEmpty(navigations)) {
            for (Navigation navigation : navigations) {
                if (NavigationType.FOLDER.equals(navigation.getType())) {
                    List<Navigation> chirdren = navigationMapper.queryList(NavigationQueryRequestBody.builder().parentId(navigation.getId()).display(true).roleId(roleId).build());
                    navigation.setChildren(chirdren);
                    bindChirdren(chirdren, roleId);
                }
            }
        }
    }

}
