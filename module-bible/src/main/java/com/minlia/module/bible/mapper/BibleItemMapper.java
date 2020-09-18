package com.minlia.module.bible.mapper;

import com.minlia.module.bible.entity.BibleItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
public interface BibleItemMapper extends BaseMapper<BibleItemEntity> {

    @MapKey("code")
    Map<String, Map<String, String>> queryValueMap(String bibleCode);
    
}