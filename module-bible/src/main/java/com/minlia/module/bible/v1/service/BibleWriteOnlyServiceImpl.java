package com.minlia.module.bible.v1.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.v1.code.BibleApiCode;
import com.minlia.module.bible.v1.dao.BibleDao;
import com.minlia.module.bible.v1.domain.Bible;
import com.minlia.module.bible.v1.domain.Bible;
import com.minlia.module.bible.v1.repository.BibleItemRepository;
import com.minlia.module.bible.v1.repository.BibleRepository;
import com.minlia.module.bible.v1.repository.BibleRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BibleWriteOnlyServiceImpl extends
    AbstractWriteOnlyService<BibleDao, Bible, Long> implements
    BibleWriteOnlyService {

  @Autowired
  BibleDao bibleItemDao;


  @Autowired
  BibleRepository bibleRepository;

  @Autowired
  BibleItemRepository bibleItemRepository;


  public Bible create(Bible bible) {
    Bible found=bibleRepository.findOneByCode(bible.getCode());
    if(null!=found){
      ApiPreconditions.checkNotNull(found, ApiCode.DATA_ALREADY_EXISTS);
    }
    return bibleRepository.save(bible);
  }

  /**
   * 当有子项时无法删除
   *
   * @param id
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
