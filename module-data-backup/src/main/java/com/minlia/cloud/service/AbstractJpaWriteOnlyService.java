package com.minlia.cloud.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by will on 10/12/17.
 */
public class AbstractJpaWriteOnlyService<REPOSITORY extends JpaRepository<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> implements
    JpaWriteOnlyService<REPOSITORY, ENTITY, PK> {

  public Class<ENTITY> clazz;

  @Autowired
  protected JpaRepository<ENTITY, PK> jpaRepository;


  public AbstractJpaWriteOnlyService() {
    Class<?> c = getClass();
    Type type = c.getGenericSuperclass();
    if (type instanceof ParameterizedType) {
      Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
      clazz = (Class<ENTITY>) parameterizedType[2];
    }
  }

  @Override
  public ENTITY save(ENTITY entity) {
    return jpaRepository.save(entity);
  }

  @Override
  public ENTITY update(ENTITY entity) {
    return jpaRepository.save(entity);
  }

  @Override
  public void delete(PK id) {
    jpaRepository.delete(id);
  }

  @Override
  public void deleteAll(Iterable<ENTITY> entities) {
    jpaRepository.delete(entities);
  }

  @Override
  public void deleteAll() {
    jpaRepository.deleteAll();
  }
}
