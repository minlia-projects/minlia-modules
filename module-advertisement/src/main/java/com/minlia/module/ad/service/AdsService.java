package com.minlia.module.ad.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.ad.bean.entity.Advertisements;
import com.minlia.module.ad.bean.ro.AdsQRO;
import com.minlia.module.ad.bean.ro.AdsCRO;
import com.minlia.module.ad.bean.ro.AdsURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdsService {

    Advertisements create(AdsCRO cro);

    Advertisements update(AdsURO uro);

    void delete(Long id);

    Advertisements queryById(Long id);

    long count(AdsQRO qro);

    Advertisements one(AdsQRO qro);

    List<Advertisements> list(AdsQRO qro);

    PageInfo<Advertisements> page(AdsQRO qro, Pageable pageable);

}
