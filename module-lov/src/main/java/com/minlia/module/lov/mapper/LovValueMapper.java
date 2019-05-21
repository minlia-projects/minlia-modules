package com.minlia.module.lov.mapper;

import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.LovValue;

import java.util.List;

public interface LovValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LovValue record);

    int insertSelective(LovValue record);

    LovValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LovValue record);

    int updateByPrimaryKey(LovValue record);

    List<LovValue> selectByAll(LovValueQRO qro);

}