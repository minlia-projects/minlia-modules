package com.minlia.cloud.service;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface IRawService<ENTITY extends Persistable,PK extends Serializable> extends IOperations<ENTITY,PK> {

    //
}
