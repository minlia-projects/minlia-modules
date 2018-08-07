package com.minlia.module.wechat.material.mapper;


import com.minlia.module.wechat.material.dto.MaterialItem;
import com.minlia.module.wechat.material.dto.WxMpMaterialBatchGetResult;

import java.util.List;

/**
 * Created by garen on 2018/8/7.
 */
public interface MaterialMapper {

    void create(WxMpMaterialBatchGetResult result);

    List<MaterialItem> queryList();

    long count();

}
