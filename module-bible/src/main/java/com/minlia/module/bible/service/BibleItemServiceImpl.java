package com.minlia.module.bible.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.body.BibleItemCreateRequestBody;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.body.BibleItemUpdateRequestBody;
import com.minlia.module.bible.constant.BibleApiCode;
import com.minlia.module.bible.entity.Bible;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.mapper.BibleItemMapper;
import com.minlia.module.bible.mapper.BibleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
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

        BibleItem bibleItem = this.queryByParentCodeAndCode(body.getParentCode(),body.getCode());
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
        mapper.map(body,bibleItem);
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
        BibleItem bibleItem = this.queryByParentCodeAndCode(parentCode,code);
        return null == bibleItem ? null : bibleItem.getValue();
    }

    @Override
    public BibleItem queryByParentCodeAndCode(String parentCode, String code) {
        return bibleItemMapper.queryOne(BibleItemQueryRequestBody.builder().parentCode(parentCode).code(code).build());
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
    public PageInfo<BibleItem> queryPage(BibleItemQueryRequestBody body, RowBounds rowBounds) {
        return bibleItemMapper.queryPage(body,rowBounds);
    }

}
