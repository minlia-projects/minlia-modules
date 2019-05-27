package com.minlia.module.lov.servcie;

import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.LovValue;

import java.util.List;

public interface LovValueService{

    int insertSelective(LovValue record);

    int updateByPrimaryKeySelective(LovValue record);

    int deleteByPrimaryKey(Long id);

    Boolean disable(Long id);

    LovValue selectByPrimaryKey(Long id);

    List<LovValue> selectByAll(LovValueQRO qro);

}