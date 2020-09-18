package com.minlia.module.ad.service.impl;

import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.ad.bean.entity.Advertisements;
import com.minlia.module.ad.bean.ro.AdQRO;
import com.minlia.module.ad.bean.ro.AdsCRO;
import com.minlia.module.ad.bean.ro.AdsQRO;
import com.minlia.module.ad.bean.ro.AdsURO;
import com.minlia.module.ad.mapper.AdsMapper;
import com.minlia.module.ad.service.AdService;
import com.minlia.module.ad.service.AdsService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class AdsServiceImpl implements AdsService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AdService adService;

    @Autowired
    private AdsMapper adsMapper;

    @Override
    public Advertisements create(AdsCRO cro) {
        Advertisements advertisements = mapper.map(cro,Advertisements.class);
        adsMapper.create(advertisements);
        return advertisements;
    }

    @Override
    public Advertisements update(AdsURO uro) {
        Advertisements advertisements = adsMapper.queryById(uro.getId());
        ApiAssert.notNull(advertisements, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uro, advertisements);
        adsMapper.update(advertisements);
        return advertisements;
    }

    @Override
    public void delete(Long id) {
        Advertisements advertisements = adsMapper.queryById(id);
        ApiAssert.notNull(advertisements, SystemCode.Message.DATA_NOT_EXISTS);

        //判断是否有子项
        long count = adService.count(AdQRO.builder().parentId(id).build());
        ApiAssert.state(count == 0, "存在广告无法删除");
        adsMapper.delete(advertisements.getId());
    }

    @Override
    public Advertisements queryById(Long id) {
        Advertisements advertisements = adsMapper.queryById(id);
        if (null != advertisements) {
            advertisements.setAdvertisements(adService.list(AdQRO.builder().parentId(id).build()));
        }
        return advertisements;
    }

    @Override
    public long count(AdsQRO qro) {
        return adsMapper.count(qro);
    }

    @Override
    public Advertisements one(AdsQRO qro) {
        return adsMapper.one(qro);
    }

    @Override
    public List<Advertisements> list(AdsQRO qro) {
        return adsMapper.list(qro);
    }

//    @Override
//    public PageInfo<Advertisements> page(AdsQRO qro, Pageable pageable) {
//        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> adsMapper.list(qro));
//    }

}
