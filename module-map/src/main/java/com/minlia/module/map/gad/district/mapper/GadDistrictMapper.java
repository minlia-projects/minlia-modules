package com.minlia.module.map.gad.district.mapper;

import com.minlia.module.map.gad.district.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.gad.district.entity.GadDistrict;

import java.util.List;

public interface GadDistrictMapper {

    void create(GadDistrict build);

    long count(GadDistrictQueryRequestBody requestBody);

    GadDistrict queryById(Long id);

    GadDistrict queryByAdcode(String adcode);

    List<GadDistrict> queryList(GadDistrictQueryRequestBody requestBody);

}
