package com.minlia.module.bible.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.bean.BibleItemCro;
import com.minlia.module.bible.bean.BibleItemUro;
import com.minlia.module.bible.constant.BibleCode;
import com.minlia.module.bible.entity.BibleEntity;
import com.minlia.module.bible.entity.BibleItemEntity;
import com.minlia.module.bible.mapper.BibleItemMapper;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.bible.service.BibleService;
import org.apache.commons.collections.MapUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
public class BibleItemServiceImpl extends ServiceImpl<BibleItemMapper, BibleItemEntity> implements BibleItemService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private BibleService bibleService;

    @Autowired
    private BibleItemMapper bibleItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(
            evict = {
                    @CacheEvict(value = "bible_item:one", allEntries = true),
                    @CacheEvict(value = "bible_item:list", allEntries = true),
                    @CacheEvict(value = "bible_item:page", allEntries = true)
            }
    )
    public BibleItemEntity create(BibleItemCro cto) {
        ApiAssert.state(bibleService.count(new QueryWrapper<BibleEntity>().lambda().eq(BibleEntity::getCode, cto.getParentCode())) == 1, BibleCode.Message.PARENT_NOT_EXISTS);
        BibleItemEntity bibleItemEntity = mapper.map(cto, BibleItemEntity.class);
        bibleItemMapper.insert(bibleItemEntity);
        return bibleItemEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(
            put = {@CachePut(value = "bible_item:id", key = "'bible_item_id:' + #p0.id")},
            evict = {
                    @CacheEvict(value = "bible_item:one", allEntries = true),
                    @CacheEvict(value = "bible_item:list", allEntries = true),
                    @CacheEvict(value = "bible_item:page", allEntries = true)
            }
    )
    public BibleItemEntity update(BibleItemUro uto) {
        ApiAssert.state(this.count(new QueryWrapper<BibleItemEntity>().lambda().eq(BibleItemEntity::getId, uto.getId())) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        BibleItemEntity entity = mapper.map(uto, BibleItemEntity.class);
        this.updateById(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(
            evict = {
                    @CacheEvict(value = "bible_item:id", key = "'bible_item_id:' + #p0"),
                    @CacheEvict(value = "bible_item:count", allEntries = true),
                    @CacheEvict(value = "bible_item:one", allEntries = true),
                    @CacheEvict(value = "bible_item:list", allEntries = true),
                    @CacheEvict(value = "bible_item:page", allEntries = true)
            }
    )
    public int delete(Long id) {
        return this.delete(id);
    }

    @Override
    @Cacheable(value = "bible_item:count", key = "'bible_item_count:' + #p0")
    public long count(BibleItemEntity entity) {
        return this.count(new QueryWrapper<BibleItemEntity>().lambda().setEntity(entity));
    }

    @Override
    @Cacheable(value = "bible_item:id", key = "'bible_item_id:' + #p0")
    public BibleItemEntity getById(Long id) {
        return this.getById(id);
    }

    @Override
    @Cacheable(value = "bible_item:one", key = "'bible_item_one:' + #p0")
    public BibleItemEntity getOne(BibleItemEntity entity) {
        return this.getOne(new QueryWrapper<BibleItemEntity>().lambda().setEntity(entity));
    }

    @Override
    @Cacheable(value = "bible_item:list", key = "'bible_item_list:' + #p0")
    public List<BibleItemEntity> list(BibleItemEntity entity) {
        return this.list(new QueryWrapper<BibleItemEntity>().lambda().setEntity(entity));
    }

    @Override
    @Cacheable(value = "bible_item:page", key = "'bible_item_page:' + #p0")
    public Page<BibleItemEntity> page(Page page, BibleItemEntity entity) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(entity);
        return this.page(page, queryWrapper);
    }

    @Override
    public String get(String parentCode, String code) {
        BibleItemEntity bibleItem = this.getOne(new QueryWrapper<BibleItemEntity>().lambda()
                .select(BibleItemEntity::getValue)
                .eq(BibleItemEntity::getParentCode, parentCode).eq(BibleItemEntity::getCode, code));
        return null == bibleItem ? null : bibleItem.getValue();
    }

    @Override
    public Map<String, String> queryValueMap(String bibleCode) {
        Map<String, Map<String, String>> source = bibleItemMapper.queryValueMap(bibleCode);
        Map<String, String> result = Maps.newHashMap();
        if (MapUtils.isNotEmpty(source)) {
            source.entrySet().stream().forEach(entry -> {
                result.put(entry.getKey(), entry.getValue().get("value"));
            });
            return result;
        }
        return result;
    }

}
