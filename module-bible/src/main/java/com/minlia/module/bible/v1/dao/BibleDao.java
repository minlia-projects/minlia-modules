package com.minlia.module.bible.v1.dao;

import com.minlia.cloud.dao.Dao;
import com.minlia.module.bible.v1.domain.Bible;

/**
 * Created by will on 8/22/17.
 */
public interface BibleDao extends Dao<Bible,Long> {
    Bible findOneByCode(String code);
}
