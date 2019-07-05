package com.minlia.module.i18n.mapper;

import com.minlia.module.i18n.entity.I18n;
import com.minlia.module.i18n.bean.I18nQRO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/20.
 */
public interface I18nMapper {

    void create(I18n i18N);

    void update(I18n i18N);

    void delete(Long id);

    I18n queryById(Long id);

    I18n queryOne(I18nQRO qro);

    List<I18n> queryList(I18nQRO qro);

    @MapKey("code")
    Map<String,String> queryMap(I18nQRO qro);

}
