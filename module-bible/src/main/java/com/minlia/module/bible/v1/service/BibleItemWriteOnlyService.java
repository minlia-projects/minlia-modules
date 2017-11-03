package com.minlia.module.bible.v1.service;


import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.module.bible.v1.dao.BibleItemDao;
import com.minlia.module.bible.v1.domain.BibleItem;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 6/17/17.
 */
@Transactional(readOnly = false)
public interface BibleItemWriteOnlyService extends WriteOnlyService<BibleItemDao, BibleItem, Long> {


  /**
   * 根据父CODE创建子项
   */
  BibleItem createItem(String code, BibleItem body);

  /**
   * 按照bible code, bibleItem code更新label
   * @param bibleCode bible code
   * @param bibleItemCode bibleItem code
   * @param bibleItemLabel bibleItem label
   * @return bibleItem
   */
  BibleItem updateLableByBibleCodeAndBibleItemCode(String bibleCode, String bibleItemCode, String bibleItemLabel);

}
