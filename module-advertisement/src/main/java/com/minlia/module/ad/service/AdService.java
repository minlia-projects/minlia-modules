package com.minlia.module.ad.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.ad.bean.ro.AdCRO;
import com.minlia.module.ad.bean.entity.Advertisement;
import com.minlia.module.ad.bean.ro.AdQRO;
import com.minlia.module.ad.bean.ro.AdURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdService {

    Advertisement create(AdCRO cro);

    Advertisement update(AdURO uro);

    void delete(Long id);

    Advertisement queryById(Long id);

    long count(AdQRO qro);

    Advertisement one(AdQRO qro);

    List<Advertisement> list(AdQRO qro);

//    PageInfo<Advertisement> page(AdQRO qro, Pageable pageable);

}
