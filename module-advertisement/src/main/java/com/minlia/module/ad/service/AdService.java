package com.minlia.module.ad.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.ad.ro.AdCRO;
import com.minlia.module.ad.entity.Advertisement;
import com.minlia.module.ad.ro.AdQRO;
import com.minlia.module.ad.ro.AdURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdService {

    Advertisement create(AdCRO cto);

    Advertisement update(AdURO uto);

    void delete(Long id);

    Advertisement queryById(Long id);

    long count(AdQRO qo);

    Advertisement one(AdQRO qo);

    List<Advertisement> list(AdQRO qo);

    PageInfo<Advertisement> page(AdQRO qo, Pageable pageable);

}
