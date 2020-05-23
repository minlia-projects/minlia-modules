package com.minlia.module.lov.servcie;

import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.LovValue;

import java.util.List;

public interface LovValueService {

    int insertSelective(LovValue record);

    int updateByPrimaryKeySelective(LovValue record);

    int deleteByPrimaryKey(Long id);

    Boolean disable(Long id);

    Boolean delete(Long id);

    LovValue selectByPrimaryKey(Long id);

    List<LovValue> selectByAll(LovValueQRO qro);

    LovValue selectOneByAll(LovValueQRO qro);

    LovValue selectOneByCodeAndLovCode(String lovCode, String code);

    String selectNameByCodeAndLovCode(String lovCode, String code);

    List<String> selectNameByAll(LovValueQRO qro);

}
