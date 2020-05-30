package com.minlia.module.data.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author william raym at minlia.com
 * Created on: 2020-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractIdentifiableType implements IdentifiableType {
    private Long id;

}
