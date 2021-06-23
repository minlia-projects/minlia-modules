package com.minlia.module.bible.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.bible.bean.BibleSro;
import com.minlia.module.bible.entity.BibleEntity;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
public interface BibleService extends IService<BibleEntity> {

    BibleEntity create(BibleSro sro);

    BibleEntity update(BibleSro sro);

    boolean delete(Long id);

    BibleEntity queryById(Long id);

    BibleEntity queryByCode(String code);

    void reload();

}