package com.minlia.module.i18n.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.i18n.bean.I18nCTO;
import com.minlia.module.i18n.bean.I18nDO;
import com.minlia.module.i18n.bean.I18nQO;
import com.minlia.module.i18n.bean.I18nUTO;
import com.minlia.module.i18n.mapper.I18nMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public I18nDO create(I18nCTO cto) {
        I18nDO i18nDO = mapper.map(cto,I18nDO.class);
        i18nMapper.create(i18nDO);
        return i18nDO;
    }

    @Override
    public I18nDO update(I18nUTO uto) {
        I18nDO i18nDO = mapper.map(uto,I18nDO.class);
        i18nMapper.update(i18nDO);
        return i18nDO;
    }

    @Override
    public void delete(Long id) {
        i18nMapper.delete(id);
    }

    @Override
    public I18nDO queryOne(Long id) {
        return i18nMapper.queryOne(id);
    }

    @Override
    public List<I18nDO> queryList(I18nQO qo) {
        return i18nMapper.queryList(qo);
    }

    @Override
    public Map<String, String> queryMap(I18nQO qo) {
        return i18nMapper.queryMap(qo);
    }

    @Override
    public PageInfo queryPage(I18nQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()->i18nMapper.queryList(qo));
    }

}
