package com.minlia.module.bible.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.body.BibleItemCreateRequestBody;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.body.BibleItemUpdateRequestBody;
import com.minlia.module.bible.code.BibleApiCode;
import com.minlia.module.bible.mapper.BibleItemMapper;
import com.minlia.module.bible.mapper.BibleMapper;
import com.minlia.module.bible.entity.Bible;
import com.minlia.module.bible.entity.BibleItem;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BibleItemServiceImpl implements BibleItemService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private BibleMapper bibleMapper;

    @Autowired
    private BibleItemMapper bibleItemMapper;

    @Override
    @Transactional
    public BibleItem create(BibleItemCreateRequestBody body) {
        Bible bible = bibleMapper.queryByCode(body.getParentCode());
        ApiPreconditions.is(null == bible, BibleApiCode.NOT_FOUND,"父级不存在");

        BibleItem bibleItem = bibleItemMapper.queryByParentCodeAndCode(body.getParentCode(),body.getCode());
        ApiPreconditions.is(null != bibleItem, BibleApiCode.DATA_ALREADY_EXISTS,"数据已存在");

        bibleItem = mapper.map(body,BibleItem.class);
        bibleItemMapper.create(bibleItem);
        return bibleItem;
    }

    @Override
    @Transactional
    public BibleItem update(BibleItemUpdateRequestBody body){
        BibleItem bibleItem=bibleItemMapper.queryById(body.getId());
        ApiPreconditions.is(null == bibleItem, BibleApiCode.NOT_FOUND,"数据不存在");
        bibleItem.setLabel(body.getLabel());
        bibleItem.setNotes(body.getNotes());
        bibleItem.setAttribute1(body.getAttribute1());
        bibleItem.setAttribute2(body.getAttribute2());
        bibleItem.setAttribute3(body.getAttribute3());
        bibleItemMapper.update(bibleItem);
        return bibleItem;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bibleItemMapper.delete(id);
    }

    @Override
    public String get(String parentCode,String code){
        BibleItem bibleItem = bibleItemMapper.queryByParentCodeAndCode(parentCode,code);
        return null == bibleItem ? null : bibleItem.getLabel();
    }

    @Override
    public BibleItem queryByParentCodeAndCode(String parentCode, String code) {
        return bibleItemMapper.queryByParentCodeAndCode(parentCode,code);
    }

    @Override
    public List<BibleItem> queryByParentCode(String parentCode) {
        return bibleItemMapper.queryList(BibleItemQueryRequestBody.builder().parentCode(parentCode).build());
    }

    @Override
    public BibleItem queryById(Long id) {
        return bibleItemMapper.queryById(id);
    }

    @Override
    public long count(BibleItemQueryRequestBody body) {
        return bibleItemMapper.count(body);
    }

    @Override
    public List<BibleItem> queryList(BibleItemQueryRequestBody body) {
        return bibleItemMapper.queryList(body);
    }

    @Override
    public PageInfo<BibleItem> queryPaginated(BibleItemQueryRequestBody body, Page page) {
        return bibleItemMapper.queryPaginated(body,page);
    }

}
