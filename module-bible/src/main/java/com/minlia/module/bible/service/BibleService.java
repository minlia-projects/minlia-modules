package com.minlia.module.bible.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.body.BibleCreateRequestBody;
import com.minlia.module.bible.body.BibleQueryRequestBody;
import com.minlia.module.bible.body.BibleUpdateRequestBody;
import com.minlia.module.bible.entity.Bible;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleService {

    Bible create(BibleCreateRequestBody requestBody);

    Bible update(BibleUpdateRequestBody requestBody);

    void delete(Long id);

    void initialBibleWithCode(String bibleCode,String code,String label,String notes,String attribute1);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQueryRequestBody requestBody);

    PageInfo<Bible> queryPage(BibleQueryRequestBody body, Pageable pageable);

}
