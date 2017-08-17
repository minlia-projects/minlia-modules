package com.minlia.cloud.dao;

import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by will on 8/17/17.
 */
@NoRepositoryBean
public interface Dao<ENTITY extends Persistable<PK>, PK extends Serializable> extends BatisDao<ENTITY,PK> {
    //No any methods here
}
