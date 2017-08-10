package com.qianyi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Created by will on 8/7/17.
 */
//@Mapper
public interface LogDao<T extends Serializable> {

    public Page<T> select(T query, Pageable pageable);
}
