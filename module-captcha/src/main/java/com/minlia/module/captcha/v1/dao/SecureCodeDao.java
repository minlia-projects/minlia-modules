package com.minlia.module.captcha.v1.dao;

import com.minlia.cloud.dao.Dao;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;

import java.util.Date;
import java.util.List;

/**
 * Created by will on 8/22/17.
 */
public interface SecureCodeDao extends Dao<SecureCode, Long> {
    List<SecureCode> findByCreatedDateBeforeAndUsed(Date dateTime, Boolean isUsed);

    List<SecureCode> findByConsumer(String username);


    List<SecureCode> findByConsumerAndCodeAndTypeAndUsed(String consumer, String code, SecureCodeSceneEnum scene, Boolean isUsed);

    List<SecureCode> findByConsumerAndCodeAndType(String consumer, String code, SecureCodeSceneEnum scene);

    List<SecureCode> findByCreatedDateBefore(Date date);
}
