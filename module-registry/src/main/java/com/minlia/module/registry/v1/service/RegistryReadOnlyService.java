package com.minlia.module.registry.v1.service;

import com.minlia.cloud.service.ReadOnlyService;
import com.minlia.module.registry.v1.dao.RegistryDao;
import com.minlia.module.registry.v1.domain.Registry;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by will on 8/27/17.
 */
@Transactional(readOnly = true)
public interface RegistryReadOnlyService extends ReadOnlyService<RegistryDao, Registry, Long> {


//需要将用到的方法CACHE化
//所以无法直接继承父接口


  /**
   * 根据全限定注册表CODE查询出当前节点下所有配置项
   * @param code
   * @return
   */
//  public List<Registry> findByCode(String code);
  @Cacheable
  public Registry findByCode(@PathVariable String code);



}
