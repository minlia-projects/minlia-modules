package com.minlia.module.ad.mapper;

import com.minlia.module.ad.bean.entity.Advertisements;
import com.minlia.module.ad.bean.ro.AdsQRO;

import java.util.List;

/**
 *
 * @author garen
 * @date 2018/2/5
 */
public interface AdsMapper {

    long create(Advertisements advertisements);

    long update(Advertisements advertisements);

    long delete(Long id);

    Advertisements queryById(Long id);

    long count(AdsQRO qro);

    Advertisements one(AdsQRO qro);

    List<Advertisements> list(AdsQRO qro);

}
