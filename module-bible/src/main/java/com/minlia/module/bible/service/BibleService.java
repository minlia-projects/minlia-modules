package com.minlia.module.bible.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.body.BibleCreateRequestBody;
import com.minlia.module.bible.body.BibleQueryRequestBody;
import com.minlia.module.bible.body.BibleUpdateRequestBody;
import com.minlia.module.bible.entity.Bible;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleService {

    Bible create(BibleCreateRequestBody body);

    Bible update(BibleUpdateRequestBody body);

    void delete(Long id);

    void initialBibleWithCode(String bibleCode,String code,String label,String notes,String attribute1);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQueryRequestBody body);

    PageInfo<Bible> queryPaginated(BibleQueryRequestBody body, Page page);

}
