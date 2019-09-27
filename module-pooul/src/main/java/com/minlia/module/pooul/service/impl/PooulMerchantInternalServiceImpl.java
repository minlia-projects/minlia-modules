package com.minlia.module.pooul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.domain.PooulMerchantDO;
import com.minlia.module.pooul.bean.qo.PooulMerchatInternalQO;
import com.minlia.module.pooul.mapper.PooulMerchantMapper;
import com.minlia.module.pooul.service.PooulMerchantInternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by garen on 2018/9/5.
 */
@Slf4j
@Service
public class PooulMerchantInternalServiceImpl implements PooulMerchantInternalService {

    @Autowired
    private PooulMerchantMapper pooulMerchantMapper;

    @Override
    public PooulMerchantDO create(PooulMerchantDO merchantDO) {
        pooulMerchantMapper.create(merchantDO);
        return merchantDO;
    }

    @Override
    public PooulMerchantDO update(PooulMerchantDO merchantDO) {
        pooulMerchantMapper.update(merchantDO);
        return merchantDO;
    }

    @Override
    public Response delete(PooulMerchantDO merchantDO) {
        return Response.is(pooulMerchantMapper.delete(merchantDO) > 0);
    }

    @Override
    public boolean exists(PooulMerchatInternalQO qo) {
        return pooulMerchantMapper.count(qo) > 0;
    }

    @Override
    public PooulMerchantDO queryOne(PooulMerchatInternalQO qo) {
        return pooulMerchantMapper.queryOne(qo);
    }

    @Override
    public List<PooulMerchantDO> queryList(PooulMerchatInternalQO qo) {
        return pooulMerchantMapper.queryList(qo);
    }

    @Override
    public PageInfo<PooulMerchantDO> queryPage(PooulMerchatInternalQO qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageNumber(),qo.getPageSize()).doSelectPageInfo(()-> pooulMerchantMapper.queryList(qo));
    }

}
