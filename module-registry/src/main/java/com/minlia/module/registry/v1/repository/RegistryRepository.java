package com.minlia.module.registry.v1.repository;

import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.module.registry.v1.domain.Registry;

/**
 * Created by will on 8/27/17.
 */
public interface RegistryRepository extends AbstractRepository<Registry,Long>{

  Registry findByCode(String code);
}
