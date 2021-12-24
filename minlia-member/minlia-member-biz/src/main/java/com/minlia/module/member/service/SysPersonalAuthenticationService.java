package com.minlia.module.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.member.bean.SysAuthenticationRo;
import com.minlia.module.member.bean.SysMemberAuthenticationSro;
import com.minlia.module.member.entity.SysPersonalAuthenticationEntity;

/**
 * <p>
 * 个人认证 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
public interface SysPersonalAuthenticationService extends IService<SysPersonalAuthenticationEntity> {

    /**
     * 认证
     *
     * @param sro
     * @return
     */
    boolean authentication(SysMemberAuthenticationSro sro);

    /**
     * 审核
     *
     * @param ro
     * @return
     */
    boolean review(SysAuthenticationRo ro);

    SysPersonalAuthenticationEntity latest();
}
