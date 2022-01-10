package com.minlia.module.bible.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.bean.BibleSro;
import com.minlia.module.bible.constant.BibleCode;
import com.minlia.module.bible.entity.BibleEntity;
import com.minlia.module.bible.entity.BibleItemEntity;
import com.minlia.module.bible.event.BibleReloadEvent;
import com.minlia.module.bible.mapper.BibleMapper;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.bible.service.BibleService;
import com.minlia.module.bible.util.BibleMapUtils;
import com.minlia.module.common.annotation.ConfigAutowired;
import com.minlia.module.dozer.util.DozerUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Service
public class BibleServiceImpl extends ServiceImpl<BibleMapper, BibleEntity> implements BibleService {

    @Autowired
    @Lazy
    private BibleItemService bibleItemService;

    @Override
    @Transactional
//    @Caching(
//            evict = {@CacheEvict(value = "minlia:bible_list",allEntries = true)}
//    )
    public BibleEntity create(BibleSro sro) {
        BibleEntity entity = DozerUtils.map(sro, BibleEntity.class);
        save(entity);
        return entity;
    }

    @Override
    @Transactional
//    @Caching(
//            put = {@CachePut(key = "'bible_id:' + #p0.id")},
//            evict = {@CacheEvict(value = "minlia:bible_list", allEntries = true)}
//    )
    public BibleEntity update(BibleSro sro) {
        ApiAssert.state(this.count(new QueryWrapper<BibleEntity>().lambda().eq(BibleEntity::getId, sro.getId())) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        BibleEntity entity = DozerUtils.map(sro, BibleEntity.class);
        updateById(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @Caching(
//            evict = {
//                    @CacheEvict(key = "'bible_id:' + #p0"),
//                    @CacheEvict(value = "bible_list", allEntries = true)
//            }
//    )
    public boolean delete(Long id) {
        BibleEntity entity = this.getById(id);
        ApiAssert.state(bibleItemService.count(new QueryWrapper<BibleItemEntity>().lambda().eq(BibleItemEntity::getParentCode, entity.getCode())) == 0, BibleCode.Message.COULD_NOT_DELETE_HAS_CHILDREN);
        return this.removeById(id);
    }

    @Override
//    @Cacheable(key = "'bible_id:' + #p0")
    public BibleEntity queryById(Long id) {
        return this.getById(id);
    }

    @Override
//    @Cacheable(key = "'bible_code:' + #p0")
    public BibleEntity queryByCode(String code) {
        return this.getOne(new QueryWrapper<BibleEntity>().lambda().eq(BibleEntity::getCode, code));
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
            DozerUtils.map(BibleMapUtils.mapToBean(map, entry.getValue().getClass()), ContextHolder.getContext().getBean(entry.getValue().getClass()));
        }
        BibleReloadEvent.onReload();
    }

}
