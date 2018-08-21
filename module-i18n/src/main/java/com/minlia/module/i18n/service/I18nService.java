package com.minlia.module.i18n.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.i18n.bean.I18nCTO;
import com.minlia.module.i18n.bean.I18nDO;
import com.minlia.module.i18n.bean.I18nQO;
import com.minlia.module.i18n.bean.I18nUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/20.
 */
public interface I18nService {

    I18nDO create(I18nCTO cto);

    I18nDO update(I18nUTO uto);

    void delete(Long id);

    I18nDO queryOne(Long id);

    List<I18nDO> queryList(I18nQO qo);

    Map<String,String> queryMap(I18nQO qo);

    PageInfo queryPage(I18nQO qo, Pageable pageable);

}
