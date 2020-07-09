package com.minlia.module.i18n.attribution.mapper;

import com.minlia.module.i18n.attribution.bean.I18nAttributionQRO;
import com.minlia.module.i18n.attribution.entity.I18nAttribution;

import java.util.List;

public interface I18nAttributionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(I18nAttribution record);

    int insertSelective(I18nAttribution record);

    I18nAttribution selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(I18nAttribution record);

    int updateByPrimaryKey(I18nAttribution record);

    List<I18nAttribution> selectByAll(I18nAttributionQRO qro);

}