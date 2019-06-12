package com.minlia.modules.rbac.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rbac.bean.domain.Navigation;
import com.minlia.modules.rbac.bean.qo.NavigationQO;
import com.minlia.modules.rbac.bean.to.NavigationCTO;
import com.minlia.modules.rbac.bean.to.NavigationGrantTO;
import com.minlia.modules.rbac.bean.to.NavigationUTO;
import com.minlia.modules.rbac.bean.vo.MyNavigationVO;
import com.minlia.modules.rbac.constant.NavigationCode;
import com.minlia.modules.rbac.constant.RoleCode;
import com.minlia.modules.rbac.enumeration.NavigationType;
import com.minlia.modules.rbac.mapper.NavigationMapper;
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
    public Response create(NavigationCTO cro) {
        boolean exists = navigationMapper.count(NavigationQO.builder().parentId(cro.getParentId()).name(cro.getName()).build()) > 0;
        if (!exists) {
            updateTypeByChildren(cro.getParentId());

            Navigation navigation = Navigation.builder().parentId(cro.getParentId()).name(cro.getName()).icon(cro.getIcon()).state(cro.getState()).orders(cro.getOrders()).type(NavigationType.LEAF).display(true).build();
            navigationMapper.create(navigation);
            return Response.success(SystemCode.Message.SUCCESS,navigation);
        } else {
            return Response.failure(NavigationCode.Message.ALREADY_EXISTS);
        }
    }

    @Override
    public Navigation update(NavigationUTO uro) {
        Navigation navigation = navigationMapper.queryById(uro.getId());
        ApiAssert.notNull(navigation, NavigationCode.Message.NOT_EXISTS);

        updateTypeByChildren(uro.getParentId());

        mapper.map(uro,navigation);
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
            ApiAssert.notNull(parent, NavigationCode.Message.PARENT_NOT_EXISTS);

            //当创建子项时设置父类为FOLDER
            if (NavigationType.LEAF.equals(parent.getType())) {
                navigationMapper.updateType(parent.getId(),NavigationType.FOLDER);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Navigation navigation = navigationMapper.queryById(id);
        ApiAssert.notNull(navigation, NavigationCode.Message.NOT_EXISTS);

        //当有子节点时报出异常
        ApiAssert.state(NavigationType.LEAF.equals(navigation.getType()), NavigationCode.Message.CAN_NOT_DELETE_HAS_CHILDREN);

        //检查父节点是否还有子节点, 如果无子节点时更新父节点为LEAF类型
        if (null != navigation.getParentId()) {
            long countChildren = navigationMapper.count(NavigationQO.builder().parentId(navigation.getParentId()).build());
            //如果只有一个儿子
            if (countChildren == 1) {
                navigationMapper.updateType(navigation.getParentId(),NavigationType.LEAF);
            }
        }
        navigationMapper.deleteMappingById(id);
        navigationMapper.delete(id);
    }

    @Override
    public void grant(NavigationGrantTO grantTO) {
        boolean existsRole = roleService.exists(grantTO.getRoleId());
        ApiAssert.state(existsRole, RoleCode.Message.NOT_EXISTS);

        navigationMapper.clear(grantTO.getRoleId());

        if (CollectionUtils.isNotEmpty(grantTO.getIds())) {
            for (Long id: grantTO.getIds()) {
                boolean exists = navigationMapper.count(NavigationQO.builder().id(id).display(true).build()) > 0;
                ApiAssert.state(exists, NavigationCode.Message.NOT_EXISTS);
            }
            navigationMapper.grant(grantTO.getRoleId(),grantTO.getIds());
        }
    }

    @Override
    public Boolean display(Long id) {
        Navigation navigation = navigationMapper.queryById(id);
        ApiAssert.notNull(navigation, NavigationCode.Message.NOT_EXISTS);
        navigationMapper.display(id,!navigation.getDisplay());
        return !navigation.getDisplay();
    }

    @Override
    public Navigation queryById(Long id) {
        return navigationMapper.queryById(id);
    }

    @Override
    public List<Navigation> queryList(NavigationQO qro) {
        List<Navigation> navigations = navigationMapper.queryList(qro);
        bindChirdren(navigations, qro.getRoleId());
        return navigations;
    }

    @Override
    public PageInfo<Navigation> queryPage(NavigationQO qro, Pageable pageable) {
        PageInfo<Navigation> pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(()-> navigationMapper.queryList(qro));
        bindChirdren(pageInfo.getList(), qro.getRoleId());
        return pageInfo;
    }

    @Override
    public List<MyNavigationVO> queryMyNavigationList(NavigationQO qro) {
//        ro.setSorts(Lists.newArrayList(new Sort("sorts", Sort.Direction.ASC)));
        List<MyNavigationVO> navigations = navigationMapper.queryMyNavigationList(qro);
        myBindChirdren(navigations, qro.getRoleId());
        return navigations;
    }

    private void bindChirdren(List<Navigation> navigations, Long roleId){
        if (CollectionUtils.isNotEmpty(navigations)) {
            for (Navigation navigation : navigations) {
                if (NavigationType.FOLDER.equals(navigation.getType())) {
                    List<Navigation> chirdren = navigationMapper.queryList(NavigationQO.builder().parentId(navigation.getId()).display(true).roleId(roleId).build());
                    navigation.setChildren(chirdren);
                    bindChirdren(chirdren, roleId);
                }
            }
        }
    }

    private void myBindChirdren(List<MyNavigationVO> navigations, Long roleId){
        if (CollectionUtils.isNotEmpty(navigations)) {
            for (MyNavigationVO navigation : navigations) {
                if (NavigationType.FOLDER.equals(navigation.getType())) {
                    List<MyNavigationVO> chirdren = navigationMapper.queryMyNavigationList(NavigationQO.builder().parentId(navigation.getId()).display(true).roleId(roleId).build());
                    navigation.setChildren(chirdren);
                    myBindChirdren(chirdren, roleId);
                }
            }
        }
    }

}
