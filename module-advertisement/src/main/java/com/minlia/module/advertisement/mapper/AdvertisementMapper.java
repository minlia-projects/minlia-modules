package com.minlia.module.advertisement.mapper;

import com.minlia.module.advertisement.bean.domain.Advertisement;
import com.minlia.module.advertisement.bean.qo.AdvertisementQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author garen
 * @date 2018/2/5
 */
@Mapper
public interface AdvertisementMapper {

    long create(Advertisement advertisement);

    long update(Advertisement advertisement);

    long delete(Long id);

    Advertisement queryById(Long id);

    long count(AdvertisementQO qo);

    Advertisement one(AdvertisementQO qo);

    List<Advertisement> list(AdvertisementQO qo);

}
