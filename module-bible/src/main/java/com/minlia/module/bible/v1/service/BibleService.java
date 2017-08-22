package com.minlia.module.bible.v1.service;


import com.minlia.boot.v1.service.IService;
import com.minlia.module.bible.v1.domain.Bible;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
@Transactional(readOnly = false)
public interface BibleService extends IService<Bible>{//},LocaclizedQueryService<Bible> {

    public static final String ENTITY = "bible";

    public static final String ENTITY_CREATE = ENTITY + ".create";
    public static final String ENTITY_UPDATE = ENTITY + ".update";
    public static final String ENTITY_DELETE = ENTITY + ".delete";
    public static final String ENTITY_READ = ENTITY + ".read";
    public static final String ENTITY_SEARCH= ENTITY + ".search";

    /**
     * 创建
     *
     * @param entity
     * @return
     */
    Bible create(Bible entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    Bible update(Bible entity);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 读取
     *
     * @param id
     * @return
     */
    Bible findOne(Long id);

    /**
     * 返回所有
     * @return
     */
    List<Bible> findAll();

}
