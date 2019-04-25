package com.minlia.module.richtext.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.richtext.bean.RichtextCTO;
import com.minlia.module.richtext.bean.RichtextQO;
import com.minlia.module.richtext.bean.RichtextUTO;
import com.minlia.module.richtext.entity.Richtext;
import com.minlia.module.richtext.mapper.RichtextMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public Richtext create(RichtextCTO cto) {
        Richtext richtext = mapper.map(cto,Richtext.class);
        richtextMapper.create(richtext);
        return richtext;
    }

    @Override
    @Transactional
    public Richtext update(RichtextUTO uto) {
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
    public long count(RichtextQO qro) {
        return richtextMapper.count(qro);
    }

    @Override
    public Richtext queryById(Long id) {
        return richtextMapper.queryById(id);
    }

    @Override
    public Richtext queryByCode(String code) {
        return richtextMapper.queryByCode(code);
    }

    @Override
    public List<Richtext> queryList(RichtextQO qro) {
        return richtextMapper.queryList(qro);
    }

    @Override
    public PageInfo<Richtext> queryPage(RichtextQO qro, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> richtextMapper.queryList(qro));
    }

}
