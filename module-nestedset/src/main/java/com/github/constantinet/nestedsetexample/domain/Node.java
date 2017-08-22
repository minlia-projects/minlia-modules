package com.github.constantinet.nestedsetexample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "nodes")
public class Node {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(name = "lft", unique = true, nullable = false)
  private Integer left;

  @Column(name = "rgt", unique = true, nullable = false)
  private Integer right;

  public Node(final Long id, final String name) {
    this.id = id;
    this.name = name;
  }

  public Node(final Long id, final String name, Integer left, Integer right) {
    this.id = id;
    this.name = name;
    this.left = left;
    this.right = right;
  }


}