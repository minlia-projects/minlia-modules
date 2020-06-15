package com.minlia.module.nestedset.node;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * A NodeDetail implementor carries information about the identity and position
 * of a node in a nested set tree.
 */
@JsonIgnoreProperties(ignoreUnknown = true,allowGetters = false,allowSetters = false)
public interface NodeDetail extends Serializable {

    Long getId();

    Long getLeftValue();

    Long getRightValue();

    Long getLevel();

    Long getRootValue();

    void setLeftValue(Long value);

    void setRightValue(Long value);

    void setLevel(Long level);

    void setRootValue(Long value);
}
