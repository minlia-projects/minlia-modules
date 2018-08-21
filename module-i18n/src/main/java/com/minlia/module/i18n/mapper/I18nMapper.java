package com.minlia.module.i18n.mapper;

import com.minlia.module.i18n.bean.I18nDO;
import com.minlia.module.i18n.bean.I18nQO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/20.
 */
public interface I18nMapper {

    void create(I18nDO i18nDO);

    void update(I18nDO i18nDO);

    void delete(Long id);

    I18nDO queryOne(Long id);

    List<I18nDO> queryList(I18nQO qo);

    @MapKey("code")
    Map<String,String> queryMap(I18nQO qo);
}
