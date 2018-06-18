package com.minlia.module.bible.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.body.BibleCreateRequestBody;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.body.BibleQueryRequestBody;
import com.minlia.module.bible.body.BibleUpdateRequestBody;
import com.minlia.module.bible.constant.BibleApiCode;
import com.minlia.module.bible.entity.Bible;
import com.minlia.module.bible.mapper.BibleMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
//@CacheConfig(cacheNames = { "minlia:bible" })
public class BibleServiceImpl implements BibleService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private BibleMapper bibleMapper;

    @Autowired
    private BibleItemService bibleItemService;

    @Override
    @Transactional
//    @Caching(
//            evict = {@CacheEvict(value = "minlia:bible_list",allEntries = true)}
//    )
    public Bible create(BibleCreateRequestBody requestBody) {
        Bible bible = bibleMapper.queryByCode(requestBody.getCode());
        ApiPreconditions.is(null != bible, ApiCode.DATA_ALREADY_EXISTS,"数据已存在");
        bible = mapper.map(requestBody,Bible.class);
        bibleMapper.create(bible);
        return bible;
    }

    @Override
    @Transactional
//    @Caching(
//            put = {@CachePut(key = "'bible_id:' + #p0.id")},
//            evict = {@CacheEvict(value = "minlia:bible_list", allEntries = true)}
//    )
    public Bible update(BibleUpdateRequestBody body) {
        Bible bible = bibleMapper.queryById(body.getId());
        ApiPreconditions.is(null == bible, ApiCode.NOT_FOUND,"数据不存在");
        bible.setValue(body.getValue());
        bible.setNotes(body.getNotes());
        bibleMapper.update(bible);
        return bible;
    }

    @Override
    @Transactional
//    @Caching(
//            evict = {
//                    @CacheEvict(key = "'bible_id:' + #p0"),
//                    @CacheEvict(value = "bible_list", allEntries = true)
//            }
//    )
    public void delete(Long id) {
        Bible bible = bibleMapper.queryById(id);
        ApiPreconditions.is(null == bible, BibleApiCode.NOT_FOUND,"数据不存在");
        ApiPreconditions.is(bibleItemService.count(BibleItemQueryRequestBody.builder().parentCode(bible.getCode()).build()) > 0, ApiCode.NOT_AUTHORIZED,"请先删除子项");
        bibleMapper.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void initialBibleWithCode(String bibleCode, String code, String label, String notes, String attribute1) {

    }

    @Override
//    @Cacheable(key = "'bible_id:' + #p0")
    public Bible queryById(Long id) {
        return bibleMapper.queryById(id);
    }

    @Override
//    @Cacheable(key = "'bible_cod:' + #p0")
    public Bible queryByCode(String code) {
        return bibleMapper.queryByCode(code);
    }

    @Override
    public List<Bible> queryList(BibleQueryRequestBody requestBody) {
        return bibleMapper.queryList(requestBody);
    }

    @Override
    public PageInfo<Bible> queryPage(BibleQueryRequestBody requestBody, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> bibleMapper.queryList(requestBody));
    }

}
