package com.minlia.module.map.gad.mapper;

import com.minlia.module.map.gad.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.gad.domain.GadDistrict;

import java.util.List;

public interface GadDistrictMapper {

    void create(GadDistrict build);

    long count(GadDistrictQueryRequestBody requestBody);

    GadDistrict queryOne(Long id);

    List<GadDistrict> queryList(GadDistrictQueryRequestBody requestBody);

}
