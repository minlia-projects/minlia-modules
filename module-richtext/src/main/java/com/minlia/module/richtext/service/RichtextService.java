package com.minlia.module.richtext.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.richtext.bean.RichtextCRO;
import com.minlia.module.richtext.bean.RichtextQRO;
import com.minlia.module.richtext.bean.RichtextURO;
import com.minlia.module.richtext.entity.Richtext;

import java.util.List;

/**
 *
 * @author garen
 * @date 2017/06/17
 */
public interface RichtextService {

    Richtext create(RichtextCRO cto);

    Richtext update(RichtextURO uto);

    void delete(Long id);

    Richtext queryById(Long id);

    Richtext queryByTypeAndCode(String type, String code);

    Richtext queryByTypeAndCode(String type, String code, String locale);

    Richtext queryByTypeAndCode(String type, String code, LocaleEnum locale);

    long count(RichtextQRO qro);

    List<Richtext> queryList(RichtextQRO qro);

    PageInfo<Richtext> queryPage(RichtextQRO qro);

}
