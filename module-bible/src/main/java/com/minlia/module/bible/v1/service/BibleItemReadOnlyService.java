package com.minlia.module.bible.v1.service;


import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.bible.v1.dao.BibleItemDao;
import com.minlia.module.bible.v1.domain.BibleItem;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 6/17/17.
 */
@Transactional(readOnly = true)
public interface BibleItemReadOnlyService extends ReadOnlyService<BibleItemDao, BibleItem, Long> {


  /**
   * 根据父CODE查找子项集
   * @param bibleCode
   * @return
   */
  Set<BibleItem> findByBible_Code(String bibleCode);

}
