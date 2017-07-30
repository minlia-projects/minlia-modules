package com.minlia.cloud.data.jpa.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by will on 6/18/17.
 */
@NoRepositoryBean
public interface AbstractRepository<ENTITY, ID extends Serializable> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY>, QueryDslPredicateExecutor<ENTITY> {

}
