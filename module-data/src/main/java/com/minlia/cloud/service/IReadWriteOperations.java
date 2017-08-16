package com.minlia.cloud.service;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Created by henry on 16/08/2017.
 */
public interface IReadWriteOperations<ENTITY extends Persistable, PK extends Serializable> extends IReadOnlyOperations, IWriteOnlyOperations{
}
