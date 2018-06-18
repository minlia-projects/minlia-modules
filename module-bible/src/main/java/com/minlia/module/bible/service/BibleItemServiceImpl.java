package com.minlia.module.bible.service;

import com.github.pagehelper.PageHelper;
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
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BibleItemServiceImpl implements BibleItemService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private BibleMapper bibleMapper;

    @Autowired
    private BibleItemMapper bibleItemMapper;

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "bible_item:one",allEntries = true),
                    @CacheEvict(value = "bible_item:list",allEntries = true),
                    @CacheEvict(value = "bible_item:page",allEntries = true)
            }
    )
    public BibleItem create(BibleItemCreateRequestBody requestBody) {
        Bible bible = bibleMapper.queryByCode(requestBody.getParentCode());
        ApiPreconditions.is(null == bible, BibleApiCode.NOT_FOUND,"父级不存在");

        BibleItem bibleItem = bibleItemMapper.queryOne(BibleItemQueryRequestBody.builder().parentCode(requestBody.getParentCode()).code(requestBody.getCode()).build());
        ApiPreconditions.is(null != bibleItem, BibleApiCode.DATA_ALREADY_EXISTS,"数据已存在");

        bibleItem = mapper.map(requestBody,BibleItem.class);
        bibleItemMapper.create(bibleItem);
        return bibleItem;
    }

    @Override
    @Transactional
    @Caching(
            put = {@CachePut(value = "bible_item:id", key = "'bible_item_id:' + #p0.id")},
            evict = {
                    @CacheEvict(value = "bible_item:one",allEntries = true),
                    @CacheEvict(value = "bible_item:list",allEntries = true),
                    @CacheEvict(value = "bible_item:page",allEntries = true)
            }
    )
    public BibleItem update(BibleItemUpdateRequestBody body){
        BibleItem bibleItem=bibleItemMapper.queryById(body.getId());
        ApiPreconditions.is(null == bibleItem, BibleApiCode.NOT_FOUND,"数据不存在");
        mapper.map(body,bibleItem);
        bibleItemMapper.update(bibleItem);
        return bibleItem;
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "bible_item:id", key = "'bible_item_id:' + #p0"),
                    @CacheEvict(value = "bible_item:count", allEntries = true),
                    @CacheEvict(value = "bible_item:one", allEntries = true),
                    @CacheEvict(value = "bible_item:list", allEntries = true),
                    @CacheEvict(value = "bible_item:page",allEntries = true)
            }
    )
    public void delete(Long id) {
        bibleItemMapper.delete(id);
    }


    @Override
    @Cacheable(value = "bible_item:count", key = "'bible_item_count:' + #p0")
    public long count(BibleItemQueryRequestBody body) {
        return bibleItemMapper.count(body);
    }

    @Override
    @Cacheable(value = "bible_item:id", key = "'bible_item_id:' + #p0")
    public BibleItem queryById(Long id) {
        return bibleItemMapper.queryById(id);
    }

    @Override
    @Cacheable(value = "bible_item:one", key = "'bible_item_one:' + #p0")
    public BibleItem queryOne(BibleItemQueryRequestBody requestBody) {
        return bibleItemMapper.queryOne(requestBody);
    }

    @Override
    @Cacheable(value = "bible_item:list", key = "'bible_item_list:' + #p0")
    public List<BibleItem> queryList(BibleItemQueryRequestBody body) {
        return bibleItemMapper.queryList(body);
    }

    @Override
    @Cacheable(value = "bible_item:page", key = "'bible_item_page:'.concat(#p1.pageNumber.toString()).concat('_').concat(#p1.pageSize.toString()).concat(#p0.toString())")
//    @Cacheable(value = "bible_item:page", key = "'bible_item_page:'.concat(#p1.pageNumber).concat(#p0).toString()")
    public PageInfo<BibleItem> queryPage(BibleItemQueryRequestBody requestBody, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> this.queryList(requestBody));
    }

    @Override
    public String get(String parentCode,String code){
        BibleItem bibleItem = bibleItemMapper.queryOne(BibleItemQueryRequestBody.builder().parentCode(parentCode).code(code).build());
        return null == bibleItem ? null : bibleItem.getValue();
    }

}
