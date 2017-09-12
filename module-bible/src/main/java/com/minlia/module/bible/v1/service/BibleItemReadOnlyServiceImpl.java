package com.minlia.module.bible.v1.service;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.module.bible.v1.dao.BibleItemDao;
import com.minlia.module.bible.v1.domain.BibleItem;
import com.minlia.module.bible.v1.repository.BibleItemRepository;
import com.minlia.module.bible.v1.repository.BibleRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BibleItemReadOnlyServiceImpl extends
    AbstractReadOnlyService<BibleItemDao, BibleItem, Long> implements
    BibleItemReadOnlyService {

  @Autowired
  BibleRepository bibleRepository;

  @Autowired
  BibleItemRepository bibleItemRepository;

  @Override
  public Set<BibleItem> findByBible_Code(String bibleCode) {
    return bibleItemRepository.findByBible_Code(bibleCode);
  }


}
