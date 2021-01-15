package com.minlia.module.email.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.email.bean.EmailSenderCro;
import com.minlia.module.email.bean.EmailSenderUro;
import com.minlia.module.email.entity.EmailSenderEntity;

/**
 * <p>
 * 邮件-发件人 服务类
 * </p>
 *
 * @author garen
 * @since 2021-01-11
 */
public interface EmailSenderService extends IService<EmailSenderEntity> {

    boolean create(EmailSenderCro cro);

    boolean update(EmailSenderUro uro);

    boolean delete(Long id);

    boolean disable(Long id);

    boolean exists(LambdaQueryWrapper lambdaQueryWrapper);

}