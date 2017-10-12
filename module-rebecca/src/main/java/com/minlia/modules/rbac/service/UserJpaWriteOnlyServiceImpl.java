package com.minlia.modules.rbac.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.service.AbstractJpaWriteOnlyService;
import com.minlia.cloud.service.AbstractWriteOnlyService;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.dao.UserDao;
import com.minlia.modules.rbac.domain.Role;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.repository.RoleRepository;
import com.minlia.modules.rbac.repository.UserRepository;
import com.minlia.modules.rbac.v1.backend.user.body.UserGarentRoleRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class UserJpaWriteOnlyServiceImpl extends
    AbstractJpaWriteOnlyService<UserRepository, User, Long> implements
    UserJpaWriteOnlyService {


  public static final String CACHE_NAME = "user";
  public static final String COUNT = CACHE_NAME + ".count";
  public static final String QUERY = CACHE_NAME + ".query";
  public static final String FIND_ONE = CACHE_NAME + ".findOne";
  public static final String P0 = "#p0";
  public static final String QUERY_KEY = "#p0+#p1";
  public static final String P0_ID = P0 + ".id";





  @Autowired
  private UserRepository repository;


  /**
   * 将系统定义的所有User相关的缓存移除
   * @param entity
   * @return
   */
  @Caching(evict = {
      @CacheEvict(cacheNames = { FIND_ONE }, key = P0),
      @CacheEvict(cacheNames = { COUNT }, allEntries = true),
      @CacheEvict(cacheNames = { QUERY }, allEntries = true)
  })
  public User update(User entity){
    return repository.save(entity);
  }



  /**
   * 将系统定义的所有User相关的缓存移除
   * @param entity
   * @return
   */
  @Caching(evict = {
      @CacheEvict(cacheNames = { FIND_ONE }, key = P0),
      @CacheEvict(cacheNames = { COUNT }, allEntries = true),
      @CacheEvict(cacheNames = { QUERY }, allEntries = true)
  })
  public User save(User entity){
    return repository.save(entity);
  }


}
