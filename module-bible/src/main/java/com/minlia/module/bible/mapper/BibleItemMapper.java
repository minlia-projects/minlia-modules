package com.minlia.module.bible.mapper;

import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.ro.BibleItemQRO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

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

    @MapKey("code")
    Map<String,String> queryValueMap(String bibleCode);

}
