package com.minlia.module.data.entity.v1;

import com.minlia.module.data.type.AbstractIdentifableType;
import com.minlia.module.data.type.IdentifableType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractIdentifableEntity extends AbstractIdentifableType implements IdentifableType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "defaultIdGenerator")
    @GenericGenerator(name = "defaultIdGenerator", strategy = "com.minlia.module.data.generator.DefaultIdentityGenerator")
    private Long id;

}
