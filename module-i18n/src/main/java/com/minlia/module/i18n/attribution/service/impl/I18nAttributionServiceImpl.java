package com.minlia.module.i18n.attribution.service.impl;

import com.minlia.module.i18n.attribution.bean.I18nAttributionQRO;
import com.minlia.module.i18n.attribution.entity.I18nAttribution;
import com.minlia.module.i18n.attribution.mapper.I18nAttributionMapper;
import com.minlia.module.i18n.attribution.service.I18nAttributionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class I18nAttributionServiceImpl implements I18nAttributionService {

    @Resource
    private I18nAttributionMapper i18nAttributionMapper;

    @Override
    public List<I18nAttribution> selectByAll(I18nAttributionQRO qro){
        return i18nAttributionMapper.selectByAll(qro);
    }
}



