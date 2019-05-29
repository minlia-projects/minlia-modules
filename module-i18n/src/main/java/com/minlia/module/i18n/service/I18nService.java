package com.minlia.module.i18n.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.i18n.ro.I18nCRO;
import com.minlia.module.i18n.entity.I18n;
import com.minlia.module.i18n.ro.I18nQRO;
import com.minlia.module.i18n.ro.I18nURO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/20.
 */
public interface I18nService {

    I18n create(I18nCRO cto);

    I18n update(I18nURO uto);

    void delete(Long id);

    I18n queryById(Long id);

    List<I18n> queryList(I18nQRO qro);

    Map<String,String> queryMap(I18nQRO qro);

    PageInfo queryPage(I18nQRO qro, Pageable pageable);

    String getValueByCode(String code);

}
