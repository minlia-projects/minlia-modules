package com.minlia.module.tencent.miniapp.repository;

import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.module.tencent.miniapp.domain.WechatUserDetail;

/**
 * Created by will on 6/25/17.
 */
public interface WechatUserDetailRepository extends AbstractRepository<WechatUserDetail,Long>{

    WechatUserDetail findByUserId(Long userId);
}
