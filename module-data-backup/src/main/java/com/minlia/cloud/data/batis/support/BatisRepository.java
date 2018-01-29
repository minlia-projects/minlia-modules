//package com.minlia.cloud.data.batis.support;
//
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mybatis.repository.support.MybatisRepository;
//import org.springframework.data.repository.NoRepositoryBean;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by will on 8/2/17.
// */
//@NoRepositoryBean
//public interface BatisRepository<ENTITY, PK extends Serializable> extends MybatisRepository<ENTITY,PK> {
//
//    List<ENTITY> findAll(boolean isBasic, Map<String, Object> paramsMap, String... columns);
//
//    List<ENTITY> findAll(boolean isBasic, Sort sort, Map<String, Object> paramsMap, String... columns);
//
//}
