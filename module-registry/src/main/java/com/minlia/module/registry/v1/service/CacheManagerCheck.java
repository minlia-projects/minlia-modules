package com.minlia.module.registry.v1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheManagerCheck implements CommandLineRunner {


  private final CacheManager cacheManager;

  public CacheManagerCheck(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @Override
  public void run(String... strings) throws Exception {
    log.info("\n\n" + "=========================================================\n"
        + "Using cache manager: " + this.cacheManager.getClass().getName() + "\n"
        + "=========================================================\n\n");
  }
}
