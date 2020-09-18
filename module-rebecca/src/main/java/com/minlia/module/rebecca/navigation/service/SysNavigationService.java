package com.minlia.module.rebecca.navigation.service;

import com.minlia.module.rebecca.navigation.bean.SysNavigationSro;
import com.minlia.module.rebecca.navigation.bean.SysNavigationVo;
import com.minlia.module.rebecca.navigation.entity.SysNavigationEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.role.bean.RoleTreeVo;

import java.util.List;

/**
 * <p>
 * 导航 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
public interface SysNavigationService extends IService<SysNavigationEntity> {

    SysNavigationEntity create(SysNavigationSro sro);

    SysNavigationEntity update(SysNavigationSro sro);

    Boolean delete(Long id);

    Boolean hide(Long id);

    List<SysNavigationVo> getMe();

    void setChildren(List<SysNavigationEntity> list, Boolean hideFlag, Boolean disFlag, String existsRole);

    /**
     * 查询组织树菜单
     *
     * @return
     */
    List<RoleTreeVo> getTree();

}
