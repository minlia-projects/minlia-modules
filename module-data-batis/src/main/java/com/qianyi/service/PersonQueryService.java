package com.qianyi.service;

import com.minlia.cloud.data.batis.service.AbstractQueryService;
import com.qianyi.dao.PersonDao;
import com.qianyi.domain.Person;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/7/17.
 */
@Service
public class PersonQueryService extends AbstractQueryService<PersonDao,Person,Long> {

}
