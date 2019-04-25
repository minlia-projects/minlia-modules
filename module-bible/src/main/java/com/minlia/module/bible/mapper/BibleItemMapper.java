package com.minlia.module.bible.mapper;

import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.ro.BibleItemQRO;

import java.util.List;

/**
 * Created by garen on 2017/8/22.
 */
public interface BibleItemMapper {

    void create(BibleItem bibleItem);

    void update(BibleItem bibleItem);

    int delete(Long id);

    long count(BibleItemQRO qro);

    BibleItem queryById(Long id);

    BibleItem queryOne(BibleItemQRO qro);

    List<BibleItem> queryList(BibleItemQRO qro);

}
