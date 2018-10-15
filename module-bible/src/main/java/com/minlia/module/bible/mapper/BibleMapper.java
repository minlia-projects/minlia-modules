package com.minlia.module.bible.mapper;

import com.minlia.module.bible.bean.domain.Bible;
import com.minlia.module.bible.bean.qo.BibleQO;

import java.util.List;

/**
 * Created by will on 8/22/17.
 */
public interface BibleMapper {

    void create(Bible bible);

    void update(Bible bible);

    void delete(Long id);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQO qo);

}
