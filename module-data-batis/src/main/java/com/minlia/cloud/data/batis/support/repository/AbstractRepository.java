/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.minlia.cloud.data.batis.support.repository;

import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface AbstractRepository<ENTITY extends AbstractAuditableEntity, PK extends Serializable> extends MybatisRepository<ENTITY, PK> {

//    ENTITY findOneById(PK id);


    ENTITY findOne(boolean isBasic, Map<String, Object> paramsMap, String... columns);

    List<ENTITY> findAll(boolean isBasic, Map<String, Object> paramsMap, String... columns);

    List<ENTITY> findAll(boolean isBasic, Sort sort, Map<String, Object> paramsMap, String... columns);

    Page<ENTITY> findAll(boolean isBasic, Pageable pageable, Map<String, Object> paramsMap, String... columns);

    Long countAll(boolean isBasic, Map<String, Object> paramsMap);

    Page<ENTITY> findAll(String selectStatement, String countStatement, Pageable pageable, Map<String, Object> paramsMap, String... columns);




}