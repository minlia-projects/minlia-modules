package com.minlia.module.bible.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.event.BibleReloadEvent;
import com.minlia.module.bible.ro.BibleItemQRO;
import com.minlia.module.bible.ro.BibleCRO;
import com.minlia.module.bible.ro.BibleQRO;
import com.minlia.module.bible.ro.BibleURO;
import com.minlia.module.bible.constant.BibleCode;
import com.minlia.module.bible.entity.Bible;
import com.minlia.module.bible.mapper.BibleMapper;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.bible.service.BibleService;
import com.minlia.module.bible.util.BibleMapUtils;
import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public Bible create(BibleCRO cto) {
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
    public Bible update(BibleURO uto) {
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
    public int delete(Long id) {
        Bible bible = bibleMapper.queryById(id);
        ApiAssert.notNull(bible, SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(bibleItemService.count(BibleItemQRO.builder().parentCode(bible.getCode()).build()) == 0, BibleCode.Message.COULD_NOT_DELETE_HAS_CHILDREN);
        return bibleMapper.delete(id);
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
    public List<Bible> queryList(BibleQRO qro) {
        return bibleMapper.queryList(qro);
    }

    @Override
    public PageInfo<Bible> queryPage(BibleQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> bibleMapper.queryList(qro));
    }

    @Override
    public void reload() {
        //获取所有带有 BibleAutowired 注解的类
        Map<String, Object> beansWithAnnotationMap = ContextHolder.getContext().getBeansWithAnnotation(ConfigAutowired.class);
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            //获取类注解
            ConfigAutowired bibleAutowired = AnnotationUtils.findAnnotation(entry.getValue().getClass(), ConfigAutowired.class);
            String bibleCode = StringUtils.isNotBlank(bibleAutowired.type()) ? bibleAutowired.type() : CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entry.getKey());

            //重新赋值
            Map<String, String> map = bibleItemService.queryValueMap(bibleCode);
            mapper.map(BibleMapUtils.mapToBean(map, entry.getValue().getClass()), ContextHolder.getContext().getBean(entry.getValue().getClass()));
        }
        BibleReloadEvent.onReload();
    }

}
