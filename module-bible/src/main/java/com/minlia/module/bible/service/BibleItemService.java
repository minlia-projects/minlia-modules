package com.minlia.module.bible.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.body.BibleItemCreateRequestBody;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.body.BibleItemUpdateRequestBody;
import com.minlia.module.bible.entity.BibleItem;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleItemService {

    BibleItem create(BibleItemCreateRequestBody requestBody);

    BibleItem update(BibleItemUpdateRequestBody requestBody);

    void delete(Long id);

    String get(String parentCode, String code);

    long count(BibleItemQueryRequestBody requestBody);

    BibleItem queryById(Long id);

    BibleItem queryByParentCodeAndCode(String parentCode, String code);

    List<BibleItem> queryByParentCode(String parentCode);

    List<BibleItem> queryList(BibleItemQueryRequestBody requestBody);

    PageInfo<BibleItem> queryPage(BibleItemQueryRequestBody requestBody, Pageable pageable);

}
