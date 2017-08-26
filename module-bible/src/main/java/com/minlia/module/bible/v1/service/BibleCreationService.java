package com.minlia.module.bible.v1.service;

import com.minlia.module.bible.v1.domain.Bible;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 7/10/17.
 * 系统初始化
 */
@Transactional(readOnly = false)
public interface BibleCreationService {

  /**
   * 初始化系统Bible配置项
   * 当有的时候不需要插入, 不存在时插入
   */
  public void initialBibleWithCode(String bibleCode, String bibleItemCode, String bibleItemLabel,
      String bibleItemNotes);


  public void initialBibleItem(String bibleItemCode, String bibleItemLabel, String bibleItemNotes,
      Bible bible);


}
