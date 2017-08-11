package com.microsoft.crm.v1.service;

import com.microsoft.crm.v1.dao.UserDao;
import com.microsoft.crm.v1.domain.User;
import com.minlia.cloud.data.batis.service.AbstractQueryService;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/7/17.
 */
@Service
public class UserQueryService extends AbstractQueryService<UserDao  ,User,Long> {

}
