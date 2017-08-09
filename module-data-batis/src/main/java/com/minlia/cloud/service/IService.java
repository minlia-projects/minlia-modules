package com.minlia.cloud.service;


import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface IService<ENTITY extends Persistable,PK extends Serializable> extends IRawService<ENTITY,PK> {

    //

}
