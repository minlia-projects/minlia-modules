package com.minlia.module.bible.mapper;

import com.minlia.module.bible.bean.domain.BibleItem;
import com.minlia.module.bible.bean.qo.BibleItemQO;

import java.util.List;

/**
 * Created by garen on 2017/8/22.
 */
public interface BibleItemMapper {

    void create(BibleItem bibleItem);

    void update(BibleItem bibleItem);

    void delete(Long id);

    long count(BibleItemQO qo);

    BibleItem queryById(Long id);

    BibleItem queryOne(BibleItemQO qo);

    List<BibleItem> queryList(BibleItemQO qo);

}
