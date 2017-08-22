package com.github.constantinet.nestedsetexample.domain;

import com.minlia.cloud.entity.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by will on 8/19/17.
 */
@Entity
@Table
@Data
public class Navigation extends AbstractEntity {

  @Column(unique = true, nullable = false)
  private String name;

  @Column(name = "lft", unique = true, nullable = false)
  private Integer left;

  @Column(name = "rgt", unique = true, nullable = false)
  private Integer right;


}
