package com.minlia.module.captcha.v1.repository;

import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;

import java.util.Date;
import java.util.List;

/**
 * Created by will on 8/24/17.
 */
public  interface SecureCodeRepository  extends AbstractRepository<SecureCode,Long>{

    List<SecureCode> findByCreatedDateBeforeAndUsed(Date dateTime, Boolean isUsed);

    List<SecureCode> findByConsumer(String username);

    List<SecureCode> findByConsumerAndCodeAndSceneAndUsed(String consumer, String code, SecureCodeSceneEnum scene, Boolean isUsed);

    List<SecureCode> findByConsumerAndCodeAndScene(String consumer, String code, SecureCodeSceneEnum scene);

    List<SecureCode> findByCreatedDateBefore(Date date);
}
