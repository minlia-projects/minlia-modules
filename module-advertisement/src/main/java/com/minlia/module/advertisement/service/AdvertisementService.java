package com.minlia.module.advertisement.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.advertisement.bean.domain.Advertisement;
import com.minlia.module.advertisement.bean.qo.AdvertisementQO;
import com.minlia.module.advertisement.bean.to.AdvertisementCTO;
import com.minlia.module.advertisement.bean.to.AdvertisementUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisementService {

    Advertisement create(AdvertisementCTO cto);

    Advertisement update(AdvertisementUTO uto);

    void delete(Long id);

    Advertisement queryById(Long id);

    long count(AdvertisementQO qo);

    Advertisement one(AdvertisementQO qo);

    List<Advertisement> list(AdvertisementQO qo);

    PageInfo<Advertisement> page(AdvertisementQO qo, Pageable pageable);

}
