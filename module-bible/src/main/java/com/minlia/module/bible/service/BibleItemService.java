package com.minlia.module.bible.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.bible.bean.BibleItemCro;
import com.minlia.module.bible.bean.BibleItemUro;
import com.minlia.module.bible.entity.BibleItemEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
public interface BibleItemService extends IService<BibleItemEntity> {

    BibleItemEntity create(BibleItemCro cto);

    BibleItemEntity update(BibleItemUro uto);

    int delete(Long id);

    long count(BibleItemEntity entity);

    BibleItemEntity getById(Long id);

    BibleItemEntity getOne(BibleItemEntity entity);

    List<BibleItemEntity> list(BibleItemEntity entity);

    Page<BibleItemEntity> page(Page page, BibleItemEntity entity);

    String get(String parentCode, String code);

    Map<String, String> queryValueMap(String bibleCode);

}
