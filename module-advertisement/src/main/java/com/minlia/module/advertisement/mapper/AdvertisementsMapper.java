package com.minlia.module.advertisement.mapper;

import com.minlia.module.advertisement.bean.domain.Advertisements;
import com.minlia.module.advertisement.bean.qo.AdvertisementsQO;

import java.util.List;

/**
 *
 * @author garen
 * @date 2018/2/5
 */
public interface AdvertisementsMapper {

    long create(Advertisements advertisements);

    long update(Advertisements advertisements);

    long delete(Long id);

    Advertisements queryById(Long id);

    long count(AdvertisementsQO qo);

    Advertisements one(AdvertisementsQO qo);

    List<Advertisements> list(AdvertisementsQO qo);

}
