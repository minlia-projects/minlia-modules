package com.minlia.module.i18n.attribution.service;

import com.minlia.module.i18n.attribution.bean.I18nAttributionQRO;
import com.minlia.module.i18n.attribution.entity.I18nAttribution;

import java.util.List;

public interface I18nAttributionService {
    List<I18nAttribution> selectByAll(I18nAttributionQRO qro);
}



