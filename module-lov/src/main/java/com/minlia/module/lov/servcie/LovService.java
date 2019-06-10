package com.minlia.module.lov.servcie;

import com.minlia.module.lov.bean.LovQRO;
import com.minlia.module.lov.enntity.Lov;

import java.util.List;

public interface LovService {

    int insertSelective(Lov record);

    int updateByPrimaryKeySelective(Lov record);

    int deleteByPrimaryKey(Long id);

    Boolean disable(Long id);

    Lov selectByPrimaryKey(Long id);

    List<Lov> selectByAll(LovQRO qro);

    Lov selectOneByCode(String code);
}

