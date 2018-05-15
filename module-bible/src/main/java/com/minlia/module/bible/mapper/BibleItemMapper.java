package com.minlia.module.bible.mapper;

import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.entity.BibleItem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 8/22/17.
 */
public interface BibleItemMapper {

    void create(BibleItem body);

    void update(BibleItem body);

    void delete(Long id);

    long count(BibleItemQueryRequestBody body);

    BibleItem queryById(Long id);

    @Cacheable(value = "minlia:bible_item", key = "'bible_item_one:' + #p0")
    BibleItem queryOne(BibleItemQueryRequestBody body);

    @Cacheable(value = "minlia:bible_item_list", key = "'bible_item_list:' + #p0")
    List<BibleItem> queryList(BibleItemQueryRequestBody body);

}
