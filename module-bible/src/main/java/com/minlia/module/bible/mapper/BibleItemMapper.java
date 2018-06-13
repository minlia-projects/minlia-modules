package com.minlia.module.bible.mapper;

import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.entity.BibleItem;
import org.springframework.cache.annotation.Cacheable;

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

//    @Cacheable(key = "'bible_item:one:' + #p0")
    BibleItem queryOne(BibleItemQueryRequestBody body);

    List<BibleItem> queryList(BibleItemQueryRequestBody body);

}
