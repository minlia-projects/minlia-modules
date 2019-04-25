package com.minlia.module.ad.mapper;

import com.minlia.module.ad.entity.Advertisements;
import com.minlia.module.ad.ro.AdsQRO;

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

    long count(AdsQRO qo);

    Advertisements one(AdsQRO qo);

    List<Advertisements> list(AdsQRO qo);

}
