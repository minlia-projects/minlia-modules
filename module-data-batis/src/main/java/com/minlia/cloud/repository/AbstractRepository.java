/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.minlia.cloud.repository;

import com.minlia.cloud.entity.AbstractEntity;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepository<ENTITY extends AbstractEntity, PK extends Serializable> extends MybatisRepository<ENTITY, PK> {



}