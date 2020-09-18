package com.minlia.module.rebecca.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.menu.bean.SysMenuSro;
import com.minlia.module.rebecca.menu.entity.SysMenuEntity;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    SysMenuEntity create(SysMenuSro sro);

    SysMenuEntity update(SysMenuSro sro);

    Boolean disable(Long id);

    Boolean hide(Long id);

}
