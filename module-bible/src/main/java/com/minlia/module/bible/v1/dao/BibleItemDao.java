package com.minlia.module.bible.v1.dao;

import com.minlia.cloud.dao.Dao;
import com.minlia.module.bible.v1.domain.BibleItem;

import java.util.Set;

/**
 * Created by will on 8/22/17.
 */
public interface BibleItemDao extends Dao<BibleItem, Long> {
    Set<BibleItem> findByBible_Code(String gender);

    BibleItem findOneByCode(String bibleItemCode);

    BibleItem findOneByBible_idAndCode(Long bibleId, String bibleItemCode);
}
