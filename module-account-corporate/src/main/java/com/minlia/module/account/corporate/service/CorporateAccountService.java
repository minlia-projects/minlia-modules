package com.minlia.module.account.corporate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.account.corporate.bean.CorporateAccountSro;
import com.minlia.module.account.corporate.entity.CorporateAccountEntity;

/**
 * <p>
 * 对公帐号 服务类
 * </p>
 *
 * @author garen
 * @since 2021-02-18
 */
public interface CorporateAccountService extends IService<CorporateAccountEntity> {

    /**
     * 保存
     *
     * @param sro
     * @return
     */
    boolean save(CorporateAccountSro sro);

    /**
     * 设置默认
     *
     * @param id
     * @return
     */
    boolean setDefault(Long id);

    /**
     * 禁用/启用
     *
     * @param id
     * @return
     */
    boolean disable(Long id);

    /**
     * 是否存在
     * @param lambdaQueryWrapper
     * @return
     */
    boolean exists(LambdaQueryWrapper lambdaQueryWrapper);

}