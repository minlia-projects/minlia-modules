package com.minlia.cloud.endpoint;

import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.cloud.service.ReadonlyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by will on 8/2/17.
 */

public abstract class AbstractEndpoint<SERVICE extends ReadonlyService,ENTITY extends AbstractEntity,PK extends Serializable>   {

    @Autowired
    protected SERVICE service;



}
