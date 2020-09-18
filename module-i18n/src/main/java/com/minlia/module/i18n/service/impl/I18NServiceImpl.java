package com.minlia.module.i18n.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.i18n.entity.I18nEntity;
import com.minlia.module.i18n.mapper.I18nMapper;
import com.minlia.module.i18n.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 国际化 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-19
 */
@Service
public class I18NServiceImpl extends ServiceImpl<I18nMapper, I18nEntity> implements I18nService {

    @Autowired
    private I18nMapper i18nMapper;

    @Override
    public Map<String, String> queryMap(I18nEntity entity) {
        return i18nMapper.queryMap(entity);
    }

    @Override
    public String getValueByCode(String code) {
        LambdaQueryWrapper<I18nEntity> queryWrapper = new QueryWrapper<I18nEntity>()
                .lambda()
                .eq(I18nEntity::getCode, code)
                .eq(I18nEntity::getLocale, LocaleContextHolder.getLocale().toString());
        I18nEntity entity = this.getOne(queryWrapper);
        return null == entity ? code : entity.getValue();
    }

}
