package com.minlia.cloud.data.jpa.support.service;

import com.minlia.cloud.data.jpa.support.operation.IOperations;
import org.springframework.data.domain.Persistable;

public interface IRawService<T extends Persistable> extends IOperations<T> {

    //
}
