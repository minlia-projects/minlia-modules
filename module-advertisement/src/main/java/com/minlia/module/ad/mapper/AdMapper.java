package com.minlia.module.ad.mapper;

import com.minlia.module.ad.bean.entity.Advertisement;
import com.minlia.module.ad.bean.ro.AdQRO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author garen
 * @date 2018/2/5
 */
@Mapper
public interface AdMapper {

    long create(Advertisement advertisement);

    long update(Advertisement advertisement);

    long delete(Long id);

    Advertisement queryById(Long id);

    long count(AdQRO qro);

    Advertisement one(AdQRO qro);

    List<Advertisement> list(AdQRO qro);

}
