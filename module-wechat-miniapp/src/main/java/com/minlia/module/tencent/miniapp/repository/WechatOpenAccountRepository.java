package com.minlia.module.tencent.miniapp.repository;

import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.module.tencent.miniapp.domain.WechatOpenAccount;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by will on 6/25/17.
 */
public interface WechatOpenAccountRepository extends AbstractRepository<WechatOpenAccount,Long>{


    @Query(nativeQuery = true,value = "UPDATE MDL_WechatOpenAccount SET userId = ?1 WHERE unionId = ?2}")
    void updateUserByUnionId(Long userId,String unionId);

    long countByUnionIdAndUserIdIsNotNull(String unionId);

    List<WechatOpenAccount> findByUnionIdAndUserIdIsNotNull(String unionId);

}
