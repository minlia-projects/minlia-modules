package com.minlia.module.bible.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.bean.domain.BibleItem;
import com.minlia.module.bible.bean.qo.BibleItemQO;
import com.minlia.module.bible.bean.to.BibleItemCTO;
import com.minlia.module.bible.bean.to.BibleItemUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleItemService {

    BibleItem create(BibleItemCTO cto);

    BibleItem update(BibleItemUTO uto);

    void delete(Long id);

    String get(String parentCode, String code);

    BibleItem queryById(Long id);

    long count(BibleItemQO qo);

    BibleItem queryOne(BibleItemQO qo);

    List<BibleItem> queryList(BibleItemQO qo);

    PageInfo<BibleItem> queryPage(BibleItemQO qo, Pageable pageable);

}
