package com.minlia.module.bible.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.bean.to.BibleItemCTO;
import com.minlia.module.bible.bean.qo.BibleItemQO;
import com.minlia.module.bible.bean.to.BibleItemUTO;
import com.minlia.module.bible.constant.BibleCode;
import com.minlia.module.bible.bean.domain.Bible;
import com.minlia.module.bible.bean.domain.BibleItem;
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
    public BibleItem create(BibleItemCTO cto) {
        Bible bible = bibleMapper.queryByCode(cto.getParentCode());
        ApiAssert.notNull(bible, BibleCode.Message.PARENT_NOT_EXISTS);

        BibleItem bibleItem = bibleItemMapper.queryOne(BibleItemQO.builder().parentCode(cto.getParentCode()).code(cto.getCode()).build());
        ApiAssert.isNull(bibleItem, SystemCode.Message.DATA_ALREADY_EXISTS);

        bibleItem = mapper.map(cto,BibleItem.class);
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
    public BibleItem update(BibleItemUTO uto){
        BibleItem bibleItem=bibleItemMapper.queryById(uto.getId());
        ApiAssert.notNull(bibleItem, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto,bibleItem);
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
    public long count(BibleItemQO qo) {
        return bibleItemMapper.count(qo);
    }

    @Override
    @Cacheable(value = "bible_item:id", key = "'bible_item_id:' + #p0")
    public BibleItem queryById(Long id) {
        return bibleItemMapper.queryById(id);
    }

    @Override
    @Cacheable(value = "bible_item:one", key = "'bible_item_one:' + #p0")
    public BibleItem queryOne(BibleItemQO qo) {
        return bibleItemMapper.queryOne(qo);
    }

    @Override
    @Cacheable(value = "bible_item:list", key = "'bible_item_list:' + #p0")
    public List<BibleItem> queryList(BibleItemQO qo) {
        return bibleItemMapper.queryList(qo);
    }

    @Override
    @Cacheable(value = "bible_item:page", key = "'bible_item_page:'.concat(#p1.pageNumber.toString()).concat('_').concat(#p1.pageSize.toString()).concat(#p0.toString())")
//    @Cacheable(value = "bible_item:page", key = "'bible_item_page:'.concat(#p1.pageNumber).concat(#p0).toString()")
    public PageInfo<BibleItem> queryPage(BibleItemQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> this.queryList(qo));
    }

    @Override
    public String get(String parentCode,String code){
        BibleItem bibleItem = bibleItemMapper.queryOne(BibleItemQO.builder().parentCode(parentCode).code(code).build());
        return null == bibleItem ? null : bibleItem.getValue();
    }

}
