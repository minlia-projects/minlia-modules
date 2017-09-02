package com.minlia.module.registry.v1.service;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.module.registry.v1.dao.RegistryDao;
import com.minlia.module.registry.v1.domain.Registry;
import com.minlia.module.registry.v1.repository.RegistryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 8/27/17.
 */
@RestController
@RequestMapping(value = "/api/v1/registries/service")
@Slf4j
@Api(tags = "Registry", description = "注册表接口")
public class RegistryReadOnlyServiceImpl extends
    AbstractReadOnlyService<RegistryDao, Registry, Long> implements RegistryReadOnlyService {


  private final RegistryRepository registryRepository;

  private final Random random;

  public RegistryReadOnlyServiceImpl(RegistryRepository registryRepository) {
    this.registryRepository = registryRepository;
    this.random = new Random();
  }

  @Scheduled(fixedDelay = 500)
  public void retrieveCountry() {
    this.findByCode("ZH");
  }


  @ApiOperation(value = "根据编码查询出所有结果", notes = "根据编码查询出所有结果", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping(value = "findByCode/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Registry findByCode(@PathVariable String code) {
    System.out.println("Looking for country with code '" + code + "'");
    return registryRepository.findByCode(code);
  }


}
