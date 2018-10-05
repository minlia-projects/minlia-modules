package com.minlia.module.advertisement.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.advertisement.bean.domain.Advertisements;
import com.minlia.module.advertisement.bean.qo.AdvertisementsQO;
import com.minlia.module.advertisement.bean.to.AdvertisementsCTO;
import com.minlia.module.advertisement.bean.to.AdvertisementsUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisementsService {

    Advertisements create(AdvertisementsCTO cto);

    Advertisements update(AdvertisementsUTO uto);

    void delete(Long id);

    Advertisements queryById(Long id);

    long count(AdvertisementsQO qo);

    Advertisements one(AdvertisementsQO qo);

    List<Advertisements> list(AdvertisementsQO qo);

    PageInfo<Advertisements> page(AdvertisementsQO qo, Pageable pageable);

}
