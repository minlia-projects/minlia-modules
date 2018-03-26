package com.minlia.module.richtext.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.richtext.body.RichtextCreateRequestBody;
import com.minlia.module.richtext.body.RichtextQueryRequestBody;
import com.minlia.module.richtext.body.RichtextUpdateRequestBody;
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
    public Richtext create(RichtextCreateRequestBody body) {
        Richtext richtext = mapper.map(body,Richtext.class);
        richtextMapper.create(richtext);
        return richtext;
    }

    @Override
    @Transactional
    public Richtext update(RichtextUpdateRequestBody body) {
        Richtext richtext = richtextMapper.queryById(body.getId());
        ApiPreconditions.is(null == richtext, ApiCode.NOT_FOUND,"记录不存在");
        mapper.map(body,richtext);
        richtextMapper.update(richtext);
        return richtext;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        richtextMapper.delete(id);
    }

    @Override
    public long count(RichtextQueryRequestBody requestBody) {
        return richtextMapper.count(requestBody);
    }

    @Override
    public Richtext queryById(Long id) {
        return richtextMapper.queryById(id);
    }

    @Override
    public List<Richtext> queryList(RichtextQueryRequestBody requestBody) {
        return richtextMapper.queryList(requestBody);
    }

    @Override
    public PageInfo<Richtext> queryPage(RichtextQueryRequestBody requestBody, Pageable pageable) {
        return PageHelper.startPage(pageable.getOffset(), pageable.getPageSize()).doSelectPageInfo(()-> richtextMapper.queryList(requestBody));
    }

}
