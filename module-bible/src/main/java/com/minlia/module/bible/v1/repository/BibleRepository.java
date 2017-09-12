package com.minlia.module.bible.v1.repository;


import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.module.bible.v1.domain.Bible;

/**
 * Created by will on 6/21/17.
 */
public interface BibleRepository extends AbstractRepository<Bible, Long> {

  Bible findOneByCode(String code);
}
