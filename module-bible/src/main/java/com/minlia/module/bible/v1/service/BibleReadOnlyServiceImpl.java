package com.minlia.module.bible.v1.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.v1.code.BibleApiCode;
import com.minlia.module.bible.v1.dao.BibleDao;
import com.minlia.module.bible.v1.domain.Bible;
import com.minlia.module.bible.v1.repository.BibleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BibleReadOnlyServiceImpl extends
    AbstractReadOnlyService<BibleDao, Bible, Long> implements BibleReadOnlyService {

  @Autowired
  BibleRepository bibleRepository;


  public Bible create(Bible bible) {
    Bible found = bibleRepository.findOneByCode(bible.getCode());
    if (null != found) {
      ApiPreconditions.checkNotNull(found, ApiCode.DATA_ALREADY_EXISTS);
    }
    return bibleRepository.save(bible);
  }

  /**
   * 当有子项时无法删除
   */
  public void delete(Long id) {
    Bible found = bibleRepository.findOne(id);
    ApiPreconditions.checkNotNull(found, BibleApiCode.NOT_FOUND);
    if (null != found.getItems() || found.getItems().size() > 0) {
      ApiPreconditions.checkNotNull(found, BibleApiCode.COULD_NOT_DELETE_HAS_CHILDREN);
    } else {
      bibleRepository.delete(id);
    }
  }


}
