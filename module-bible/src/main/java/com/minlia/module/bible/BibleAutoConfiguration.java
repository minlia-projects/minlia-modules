package com.minlia.module.bible;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 6/21/17.
 */
@Configuration
@MapperScan(basePackages={"com.minlia.module.*.mapper"})
public class BibleAutoConfiguration {

}
