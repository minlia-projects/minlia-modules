package com.minlia.module.i18n.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.i18n.entity.I18n;
import com.minlia.module.i18n.mapper.I18nMapper;
import com.minlia.module.i18n.ro.I18nCRO;
import com.minlia.module.i18n.ro.I18nQRO;
import com.minlia.module.i18n.ro.I18nURO;
import com.minlia.module.i18n.service.I18nService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/20.
 */
@Service
public class I18nServiceImpl implements I18nService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private I18nMapper i18nMapper;

    @Override
    public I18n create(I18nCRO cto) {
        I18n i18N = mapper.map(cto, I18n.class);
        i18nMapper.create(i18N);
        return i18N;
    }

    @Override
    public I18n update(I18nURO uto) {
        I18n i18N = mapper.map(uto, I18n.class);
        i18nMapper.update(i18N);
        return i18N;
    }

    @Override
    public void delete(Long id) {
        i18nMapper.delete(id);
    }

    @Override
    public I18n queryById(Long id) {
        return i18nMapper.queryById(id);
    }

    @Override
    public List<I18n> queryList(I18nQRO qro) {
        return i18nMapper.queryList(qro);
    }

    @Override
    public Map<String, String> queryMap(I18nQRO qro) {
        return i18nMapper.queryMap(qro);
    }

    @Override
    public PageInfo queryPage(I18nQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(),qro.getPageSize()).doSelectPageInfo(()->i18nMapper.queryList(qro));
    }

    @Override
    public String getValueByCode(String code) {
        I18n i18n = i18nMapper.queryOne(I18nQRO.builder().code(code).locale(LocaleContextHolder.getLocale().toString()).build());
        if (null != i18n) {
            return i18n.getValue();
        }
        return code;
    }

}
