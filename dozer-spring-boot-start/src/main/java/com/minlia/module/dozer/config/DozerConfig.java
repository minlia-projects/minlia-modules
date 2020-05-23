package com.minlia.module.dozer.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiyeon on 15. 9. 6..
 */
@EnableAutoConfiguration
@Configuration
public class DozerConfig {

    @Bean(name = "org.dozer.Mapper")
    public Mapper dozerBean() {
        List<String> mappingFiles = Arrays.asList("dozer/dozer-configration-mapping.xml");

        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

}
