package com.minlia.module.richtext.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.richtext.bean.RichtextCRO;
import com.minlia.module.richtext.bean.RichtextQRO;
import com.minlia.module.richtext.bean.RichtextURO;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.mapper.RichtextMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RichtextServiceImpl implements RichtextService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RichtextMapper richtextMapper;

    @Override
    @Transactional
    public Richtext create(RichtextCRO cto) {
        Richtext richtext = mapper.map(cto,Richtext.class);
        richtextMapper.create(richtext);
        return richtext;
    }

    @Override
    @Transactional
    public Richtext update(RichtextURO uto) {
        Richtext richtext = richtextMapper.queryById(uto.getId());
        ApiAssert.notNull(richtext, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto,richtext);
        richtextMapper.update(richtext);
        return richtext;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        richtextMapper.delete(id);
    }

    @Override
    public long count(RichtextQRO qro) {
        return richtextMapper.count(qro);
    }

    @Override
    public Richtext queryById(Long id) {
        return richtextMapper.queryById(id);
    }

    @Override
    public Richtext queryByTypeAndCode(String type, String code) {
        return this.queryByTypeAndCode(type, code, LocaleContextHolder.getLocale().toString());
    }

    @Override
    public Richtext queryByTypeAndCode(String type, String code, String locale) {
        return this.queryByTypeAndCode(type, code, LocaleEnum.valueOf(locale));
    }

    @Override
    public Richtext queryByTypeAndCode(String type, String code, LocaleEnum locale) {
        return richtextMapper.queryOne(RichtextQRO.builder().type(type).code(code).locale(locale).build());
    }

    @Override
    public List<Richtext> queryList(RichtextQRO qro) {
        return richtextMapper.queryList(qro);
    }

    @Override
    public PageInfo<Richtext> queryPage(RichtextQRO qro) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(()-> richtextMapper.queryList(qro));
    }

}
