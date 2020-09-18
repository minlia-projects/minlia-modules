package com.minlia.module.i18n.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minlia.module.i18n.entity.I18nEntity;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
 * <p>
 * 国际化 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-08-19
 */
public interface I18nMapper extends BaseMapper<I18nEntity> {

    @MapKey("code")
    Map<String, String> queryMap(I18nEntity entity);

}