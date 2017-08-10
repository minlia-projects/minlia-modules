package com.minlia.cloud.dao;

import org.springframework.data.domain.Persistable;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by will on 8/3/17.
 */
@NoRepositoryBean
public interface BatisDao<ENTITY extends Persistable<PK>, PK extends Serializable> extends MybatisRepository<ENTITY, PK> {

    // extends BaseMapper<ENTITY>

}
