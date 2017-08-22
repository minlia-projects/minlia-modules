package com.minlia.module.bible.v1.service;


import com.minlia.boot.v1.service.IService;
import com.minlia.module.bible.v1.domain.BibleItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by will on 6/17/17.
 */
@Transactional(readOnly = false)
public interface BibleItemService extends IService<BibleItem>{//},LocaclizedQueryService<BibleItem> {

    public static final String ENTITY = "bible";

    public static final String ENTITY_CREATE = ENTITY + ".create";
    public static final String ENTITY_UPDATE = ENTITY + ".update";
    public static final String ENTITY_DELETE = ENTITY + ".delete";
    public static final String ENTITY_READ = ENTITY + ".read";
//    public static final String ENTITY_READ_BY_ID = ENTITY + ".read.byId";
//    public static final String ENTITY_GRANT = ENTITY + ".grant";

    /**
     * 创建
     *
     * @param entity
     * @return
     */
    BibleItem create(BibleItem entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    BibleItem update(BibleItem entity);

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
    BibleItem findOne(Long id);

    /**
     * 返回所有
     * @return
     */
    List<BibleItem> findAll();

    /**
     * 根据父CODE创建子项
     * @param code
     * @param body
     * @return
     */
    BibleItem createItem(String code, BibleItem body);

    /**
     * 根据父CODE查找子项集
     * @param bibleCode
     * @return
     */
    Set<BibleItem> findByBible_Code(String bibleCode);
}
