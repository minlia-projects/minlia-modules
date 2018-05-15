package com.minlia.module.bible.mapper;

import com.minlia.module.bible.body.BibleQueryRequestBody;
import com.minlia.module.bible.entity.Bible;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 8/22/17.
 */
@CacheConfig(cacheNames = { "minlia:bible" })
public interface BibleMapper {

    void create(Bible bible);

    void update(Bible bible);

    void delete(Long id);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    @Cacheable(value = "minlia:bible_list", key = "'bible_list:' + #p0")
    List<Bible> queryList(BibleQueryRequestBody body);

}
