package com.minlia.cloud.service;

import com.minlia.cloud.dao.BatisDao;
import java.io.Serializable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by will on 10/12/17.
 */
public interface JpaWriteOnlyService <REPOSITORY extends JpaRepository<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> {

  /**
   * save entity
   *
   * @param entity
   * @return
   */
  public ENTITY save(ENTITY entity);

  /**
   * update entity
   *
   * @param entity
   * @return
   */
  public ENTITY update(ENTITY entity);

  /**
   * delete by id
   *
   * @param id
   */
  public void delete(PK id);

  /**
   * delete all by entity list
   *
   * @param entities
   */
  public void deleteAll(Iterable<ENTITY> entities);

  /**
   * delete all
   */
  public void deleteAll();

}
