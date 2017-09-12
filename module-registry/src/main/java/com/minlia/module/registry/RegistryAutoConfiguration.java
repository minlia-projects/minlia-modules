package com.minlia.module.registry;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 6/19/17.
 * 启动时延迟此BEAN初始化
 */
@Configuration
@EnableCaching
@CacheConfig(cacheNames = "registry")//TODO 当有多个应用使用此模块时会出现都使用此缓存的情产况, 需进一步解决
public class RegistryAutoConfiguration {

}
