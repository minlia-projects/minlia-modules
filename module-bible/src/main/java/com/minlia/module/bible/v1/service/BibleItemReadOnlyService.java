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
   * @param bibleCode 父code
   * @return bibleItem集合
   */
  Set<BibleItem> findByBible_Code(String bibleCode);

  /**
   * 根据bible code及bibleItem code获取label值
   * @param bibleCode bible code
   * @param itemCode bibleItem code
   * @return label值
   */
  String getLabelByBibleCodeAndBibleItemCode(String bibleCode,String itemCode);

  /**
   * 根据bible code及bibleItem code获取bibleItem
   * @param bibleCode bible code
   * @param itemCode bibleItem code
   * @return bibleItem
   */
  BibleItem findByBibleCodeAndBibleItemCode(String bibleCode, String itemCode);

}
