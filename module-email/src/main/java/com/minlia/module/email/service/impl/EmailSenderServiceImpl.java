package com.minlia.module.email.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.email.bean.EmailSenderCro;
import com.minlia.module.email.bean.EmailSenderUro;
import com.minlia.module.email.entity.EmailSenderEntity;
import com.minlia.module.email.holder.EmailSenderHolder;
import com.minlia.module.email.mapper.EmailSenderMapper;
import com.minlia.module.email.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 邮件-发件人 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-01-11
 */
@Service
public class EmailSenderServiceImpl extends ServiceImpl<EmailSenderMapper, EmailSenderEntity> implements EmailSenderService {

    @Autowired
    @Lazy
    private EmailSenderHolder emailSenderHolder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(EmailSenderCro cro) {
        EmailSenderEntity entity = DozerUtils.map(cro, EmailSenderEntity.class);
        boolean existsDefault = this.exists(Wrappers.<EmailSenderEntity>lambdaQuery().eq(EmailSenderEntity::getDefFlag, true));
        if (!existsDefault) {
            entity.setDefFlag(true);
        }
        this.save(entity);
        emailSenderHolder.add(entity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(EmailSenderUro uro) {
        EmailSenderEntity entity = DozerUtils.map(uro, EmailSenderEntity.class);
        if (entity.getDefFlag()) {
            this.update(Wrappers.<EmailSenderEntity>lambdaUpdate().set(EmailSenderEntity::getDefFlag, false));
        }
        this.updateById(entity);

        EmailSenderEntity entityFound = this.getById(uro.getId());
        emailSenderHolder.add(entityFound);
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        EmailSenderEntity entity = this.getById(id);
        emailSenderHolder.remove(entity.getCode());
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disable(Long id) {
        EmailSenderEntity entity = this.getById(id);
        entity.setDisFlag(!entity.getDisFlag());
        this.updateById(entity);
        if (entity.getDisFlag()) {
            emailSenderHolder.remove(entity.getCode());
        } else {
            emailSenderHolder.add(entity);
        }
        return entity.getDisFlag();
    }

    @Override
    public boolean exists(LambdaQueryWrapper lambdaQueryWrapper) {
        return this.count(lambdaQueryWrapper) > 0;
    }

}