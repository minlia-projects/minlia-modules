package com.minlia.module.bible.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.ro.BibleItemQRO;
import com.minlia.module.bible.ro.BibleItemCRO;
import com.minlia.module.bible.ro.BibleItemURO;
import com.minlia.module.bible.constant.BibleCode;
import com.minlia.module.bible.entity.Bible;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.mapper.BibleItemMapper;
import com.minlia.module.bible.mapper.BibleMapper;
import com.minlia.module.bible.service.BibleItemService;
import org.apache.commons.collections.MapUtils;
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
import java.util.Map;

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
    public BibleItem create(BibleItemCRO cto) {
        Bible bible = bibleMapper.queryByCode(cto.getParentCode());
        ApiAssert.notNull(bible, BibleCode.Message.PARENT_NOT_EXISTS);

        BibleItem bibleItem = bibleItemMapper.queryOne(BibleItemQRO.builder().parentCode(cto.getParentCode()).code(cto.getCode()).build());
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
    public BibleItem update(BibleItemURO uto){
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
    public int delete(Long id) {
        return bibleItemMapper.delete(id);
    }


    @Override
    @Cacheable(value = "bible_item:count", key = "'bible_item_count:' + #p0")
    public long count(BibleItemQRO qro) {
        return bibleItemMapper.count(qro);
    }

    @Override
    @Cacheable(value = "bible_item:id", key = "'bible_item_id:' + #p0")
    public BibleItem queryById(Long id) {
        return bibleItemMapper.queryById(id);
    }

    @Override
    @Cacheable(value = "bible_item:one", key = "'bible_item_one:' + #p0")
    public BibleItem queryOne(BibleItemQRO qro) {
        return bibleItemMapper.queryOne(qro);
    }

    @Override
    @Cacheable(value = "bible_item:list", key = "'bible_item_list:' + #p0")
    public List<BibleItem> queryList(BibleItemQRO qro) {
        return bibleItemMapper.queryList(qro);
    }

    @Override
    @Cacheable(value = "bible_item:page", key = "'bible_item_page:'.concat(#p1.pageNumber.toString()).concat('_').concat(#p1.pageSize.toString()).concat(#p0.toString())")
//    @Cacheable(value = "bible_item:page", key = "'bible_item_page:'.concat(#p1.pageNumber).concat(#p0).toString()")
    public PageInfo<BibleItem> queryPage(BibleItemQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(()-> this.queryList(qro));
    }

    @Override
    public String get(String parentCode,String code){
        BibleItem bibleItem = bibleItemMapper.queryOne(BibleItemQRO.builder().parentCode(parentCode).code(code).build());
        return null == bibleItem ? null : bibleItem.getValue();
    }

    @Override
    public Map<String, String> queryValueMap(String bibleCode) {
        Map<String, Map<String, String>> source = bibleItemMapper.queryValueMap(bibleCode);
        Map<String, String> result = Maps.newHashMap();
        if (MapUtils.isNotEmpty(source)) {
            source.entrySet().stream().forEach(entry ->{
                result.put(entry.getKey(), entry.getValue().get("value"));
            });
            return result;
        }
        return result;
    }

}
