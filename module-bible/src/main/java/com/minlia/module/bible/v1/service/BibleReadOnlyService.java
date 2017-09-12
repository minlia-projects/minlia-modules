package com.minlia.module.bible.v1.service;


import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.bible.v1.dao.BibleDao;
import com.minlia.module.bible.v1.domain.Bible;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 6/17/17.
 */
@Transactional(readOnly = false)
public interface BibleReadOnlyService extends ReadOnlyService<BibleDao, Bible, Long> {


}
