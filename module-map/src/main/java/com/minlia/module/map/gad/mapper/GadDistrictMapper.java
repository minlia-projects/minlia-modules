package com.minlia.module.map.gad.mapper;

import com.minlia.module.map.gad.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.gad.entity.GadDistrict;

import java.util.List;

public interface GadDistrictMapper {

    void create(GadDistrict build);

    long count(GadDistrictQueryRequestBody requestBody);

    GadDistrict queryById(Long id);

    GadDistrict queryByAdcode(String adcode);

    List<GadDistrict> queryList(GadDistrictQueryRequestBody requestBody);

}
