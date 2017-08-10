package com.qianyi.service;

import com.minlia.cloud.service.AbstractService;
import com.qianyi.dao.PersonDao;
import com.qianyi.domain.Person;
import com.qianyi.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/2/17.
 */
@Service
@Slf4j
public class PersonServiceImpl extends AbstractService<PersonRepository, Person, Long> implements PersonService {


    @Autowired
    PersonDao personDao;




}
