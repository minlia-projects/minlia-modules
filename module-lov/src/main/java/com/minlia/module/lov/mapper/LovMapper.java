package com.minlia.module.lov.mapper;
import com.minlia.module.lov.bean.LovQRO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Date;

import com.minlia.module.lov.enntity.Lov;

public interface LovMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Lov record);

    int insertSelective(Lov record);

    Lov selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Lov record);

    int updateByPrimaryKey(Lov record);


    List<Lov> selectByAll(LovQRO qro);

}