package com.minlia.module.bible.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.ro.BibleItemQRO;
import com.minlia.module.bible.ro.BibleItemCRO;
import com.minlia.module.bible.ro.BibleItemURO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by will on 6/17/17.
 */
public interface BibleItemService {

    BibleItem create(BibleItemCRO cto);

    BibleItem update(BibleItemURO uto);

    int delete(Long id);

    String get(String parentCode, String code);

    BibleItem queryById(Long id);

    long count(BibleItemQRO qro);

    BibleItem queryOne(BibleItemQRO qro);

    List<BibleItem> queryList(BibleItemQRO qro);

    PageInfo<BibleItem> queryPage(BibleItemQRO qro, Pageable pageable);

    Map<String, String> queryValueMap(String bibleCode);
}
