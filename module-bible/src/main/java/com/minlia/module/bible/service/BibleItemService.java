package com.minlia.module.bible.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.body.BibleItemCreateRequestBody;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.body.BibleItemUpdateRequestBody;
import com.minlia.module.bible.entity.BibleItem;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleItemService {

    BibleItem create(BibleItemCreateRequestBody body);

    BibleItem update(BibleItemUpdateRequestBody body);

    void delete(Long id);

    String get(String parentCode, String code);

    BibleItem queryByParentCodeAndCode(String parentCode, String code);

    List<BibleItem> queryByParentCode(String parentCode);

    BibleItem queryById(Long id);

    long count(BibleItemQueryRequestBody body);

    List<BibleItem> queryList(BibleItemQueryRequestBody body);

    PageInfo<BibleItem> queryPage(BibleItemQueryRequestBody body, RowBounds rowBounds);

}
