package com.qianyi.service;

import com.minlia.cloud.service.IService;
import com.qianyi.domain.Person;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/2/17.
 */
@Transactional
public interface PersonService extends IService<Person, Long> {

}
