package com.minlia.module.bible.mapper;

import com.minlia.module.bible.entity.Bible;
import com.minlia.module.bible.ro.BibleQRO;

import java.util.List;

/**
 * Created by will on 8/22/17.
 */
public interface BibleMapper {

    int create(Bible bible);

    int update(Bible bible);

    int delete(Long id);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQRO qro);

}
