package com.minlia.module.disrtict.mapper;

import com.minlia.module.disrtict.bean.domain.District;
import com.minlia.module.disrtict.bean.qo.DistrictQO;

import java.util.List;

public interface DistrictMapper {

    void create(District district);

    District queryById(Long id);

    District queryByAdCode(String adcode);

    long count(DistrictQO qo);

    List<District> queryList(DistrictQO qo);

}

