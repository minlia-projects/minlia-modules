package com.minlia.module.bible.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.ro.BibleCRO;
import com.minlia.module.bible.ro.BibleQRO;
import com.minlia.module.bible.ro.BibleURO;
import com.minlia.module.bible.entity.Bible;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleService {

    Bible create(BibleCRO cto);

    Bible update(BibleURO uto);

    int delete(Long id);

    void initialBibleWithCode(String bibleCode,String code,String label,String notes,String attribute1);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQRO qro);

    PageInfo<Bible> queryPage(BibleQRO qro, Pageable pageable);

    void reload();

}
