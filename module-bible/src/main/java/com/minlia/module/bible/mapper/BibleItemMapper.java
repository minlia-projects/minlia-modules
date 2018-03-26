package com.minlia.module.bible.mapper;

import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.entity.BibleItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 8/22/17.
 */
@Component
public interface BibleItemMapper {

    void create(BibleItem body);

    void update(BibleItem body);

    void delete(Long id);

    long count(BibleItemQueryRequestBody requestBody);

    BibleItem queryById(Long id);

    BibleItem queryOne(BibleItemQueryRequestBody build);

    List<BibleItem> queryList(BibleItemQueryRequestBody requestBody);

}
