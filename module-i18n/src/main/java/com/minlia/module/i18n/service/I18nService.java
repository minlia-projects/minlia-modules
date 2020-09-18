package com.minlia.module.i18n.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.i18n.entity.I18nEntity;

import java.util.Map;

/**
 * <p>
 * 国际化 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-19
 */
public interface I18nService extends IService<I18nEntity> {

    Map<String, String> queryMap(I18nEntity entity);

    String getValueByCode(String code);

}
