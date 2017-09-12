package com.minlia.module.bible.v1.service;

import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.v1.code.BibleApiCode;
import com.minlia.module.bible.v1.dao.BibleItemDao;
import com.minlia.module.bible.v1.domain.Bible;
import com.minlia.module.bible.v1.domain.BibleItem;
import com.minlia.module.bible.v1.repository.BibleItemRepository;
import com.minlia.module.bible.v1.repository.BibleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BibleItemWriteOnlyServiceImpl extends
    AbstractWriteOnlyService<BibleItemDao, BibleItem, Long> implements
    BibleItemWriteOnlyService {

  @Autowired
  BibleItemDao bibleItemDao;


  @Autowired
  BibleRepository bibleRepository;

  @Autowired
  BibleItemRepository bibleItemRepository;


  @Override
  public BibleItem createItem(String code, BibleItem body) {
    Bible parentEntity = bibleRepository.findOneByCode(code);
    ApiPreconditions.checkNotNull(parentEntity, BibleApiCode.NOT_FOUND);
    body.setBible(parentEntity);
    BibleItem entityCreated = bibleItemRepository.save(body);
    return entityCreated;
  }


  public BibleItem update(BibleItem entity) {

    BibleItem entityFound = bibleItemRepository.findOne(entity.getId());
    ApiPreconditions.checkNotNull(entityFound, BibleApiCode.NOT_FOUND);

    entityFound.setCode(entity.getCode());
    entityFound.setLabel(entity.getLabel());
    entityFound.setNotes(entity.getNotes());

    BibleItem updated = bibleItemRepository.save(entityFound);
    return updated;
  }

}
