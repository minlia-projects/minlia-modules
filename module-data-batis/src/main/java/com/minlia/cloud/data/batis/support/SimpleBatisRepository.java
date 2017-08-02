package com.minlia.cloud.data.batis.support;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.support.MybatisEntityInformation;
import org.springframework.data.mybatis.repository.support.SimpleMybatisRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class SimpleBatisRepository<ENTITY, PK extends Serializable> extends SimpleMybatisRepository<ENTITY,PK> implements BatisRepository<ENTITY,PK>{

    public SimpleBatisRepository(MybatisEntityInformation entityInformation, SqlSessionTemplate sqlSessionTemplate) {
        super(entityInformation, sqlSessionTemplate);
    }

    @Override
    public List<ENTITY> findAll(boolean isBasic, Map<String, Object> paramsMap, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(paramsMap);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectList(isBasic ? "_findBasicAll" : "_findAll", params);
    }

    @Override
    public List<ENTITY> findAll(boolean isBasic, Sort sort, Map<String, Object> paramsMap, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(paramsMap);
        params.put("_sorts", sort);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectList(isBasic ? "_findBasicAll" : "_findAll", params);
    }
}