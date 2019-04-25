package com.minlia.module.ad.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.ad.entity.Advertisements;
import com.minlia.module.ad.ro.AdsQRO;
import com.minlia.module.ad.ro.AdsCRO;
import com.minlia.module.ad.ro.AdsURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdsService {

    Advertisements create(AdsCRO cto);

    Advertisements update(AdsURO uto);

    void delete(Long id);

    Advertisements queryById(Long id);

    long count(AdsQRO qo);

    Advertisements one(AdsQRO qo);

    List<Advertisements> list(AdsQRO qo);

    PageInfo<Advertisements> page(AdsQRO qo, Pageable pageable);

}
