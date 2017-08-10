package com.qianyi.domain;

import com.minlia.cloud.entity.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "iphone")
@Getter
@Setter

public class Iphone extends AbstractAuditingEntity {


    private String size;
    private String color;


}
