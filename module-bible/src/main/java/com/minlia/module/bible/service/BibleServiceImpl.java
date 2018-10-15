package com.minlia.module.bible.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.bean.to.BibleCTO;
import com.minlia.module.bible.bean.qo.BibleItemQO;
import com.minlia.module.bible.bean.qo.BibleQO;
import com.minlia.module.bible.bean.to.BibleUTO;
import com.minlia.module.bible.constant.BibleCode;
import com.minlia.module.bible.bean.domain.Bible;
import com.minlia.module.bible.mapper.BibleMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Bible create(BibleCTO cto) {
        Bible bible = bibleMapper.queryByCode(cto.getCode());
        ApiAssert.isNull(bible, SystemCode.Message.DATA_ALREADY_EXISTS);
        bible = mapper.map(cto,Bible.class);
        bibleMapper.create(bible);
        return bible;
    }

    @Override
    @Transactional
//    @Caching(
//            put = {@CachePut(key = "'bible_id:' + #p0.id")},
//            evict = {@CacheEvict(value = "minlia:bible_list", allEntries = true)}
//    )
    public Bible update(BibleUTO uto) {
        Bible bible = bibleMapper.queryById(uto.getId());
        ApiAssert.notNull(bible, SystemCode.Message.DATA_NOT_EXISTS);
        bible.setValue(uto.getValue());
        bible.setNotes(uto.getNotes());
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
        ApiAssert.notNull(bible, SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(bibleItemService.count(BibleItemQO.builder().parentCode(bible.getCode()).build()) == 0, BibleCode.Message.COULD_NOT_DELETE_HAS_CHILDREN);
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
    public List<Bible> queryList(BibleQO qo) {
        return bibleMapper.queryList(qo);
    }

    @Override
    public PageInfo<Bible> queryPage(BibleQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> bibleMapper.queryList(qo));
    }

}
