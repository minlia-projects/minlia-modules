package com.minlia.module.advertisement.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.advertisement.bean.domain.Advertisements;
import com.minlia.module.advertisement.bean.qo.AdvertisementQO;
import com.minlia.module.advertisement.bean.qo.AdvertisementsQO;
import com.minlia.module.advertisement.bean.to.AdvertisementsCTO;
import com.minlia.module.advertisement.bean.to.AdvertisementsUTO;
import com.minlia.module.advertisement.mapper.AdvertisementsMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class AdvertisementsServiceImpl implements AdvertisementsService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AdvertisementsMapper advertisementsMapper;

    @Override
    public Advertisements create(AdvertisementsCTO cto) {
        Advertisements advertisements = mapper.map(cto,Advertisements.class);
        advertisementsMapper.create(advertisements);
        return advertisements;
    }

    @Override
    public Advertisements update(AdvertisementsUTO uto) {
        Advertisements advertisements = advertisementsMapper.queryById(uto.getId());
        ApiAssert.notNull(advertisements, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, advertisements);
        advertisementsMapper.update(advertisements);
        return advertisements;
    }

    @Override
    public void delete(Long id) {
        Advertisements advertisements = advertisementsMapper.queryById(id);
        ApiAssert.notNull(advertisements, SystemCode.Message.DATA_NOT_EXISTS);

        //判断是否有子项
        long count = advertisementService.count(AdvertisementQO.builder().parentId(id).build());
        ApiAssert.state(count == 0, "存在广告无法删除");
        advertisementsMapper.delete(advertisements.getId());
    }

    @Override
    public Advertisements queryById(Long id) {
        Advertisements advertisements = advertisementsMapper.queryById(id);
        if (null != advertisements) {
            advertisements.setAdvertisements(advertisementService.list(AdvertisementQO.builder().parentId(id).build()));
        }
        return advertisements;
    }

    @Override
    public long count(AdvertisementsQO qo) {
        return advertisementsMapper.count(qo);
    }

    @Override
    public Advertisements one(AdvertisementsQO qo) {
        return advertisementsMapper.one(qo);
    }

    @Override
    public List<Advertisements> list(AdvertisementsQO qo) {
        return advertisementsMapper.list(qo);
    }

    @Override
    public PageInfo<Advertisements> page(AdvertisementsQO qo, Pageable pageable) {
        return PageHelper.startPage(qo.getPageNumber(), qo.getPageSize()).doSelectPageInfo(()->advertisementsMapper.list(qo));
    }

}
