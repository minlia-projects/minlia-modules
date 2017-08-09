/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.minlia.cloud.repository;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepository<ENTITY extends Persistable, PK extends Serializable> extends JpaRepository<ENTITY, PK>,QueryDslPredicateExecutor<ENTITY>,JpaSpecificationExecutor<ENTITY> {



}