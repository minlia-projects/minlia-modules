package com.minlia.module.bible.v1.service;


import com.minlia.cloud.service.WriteOnlyService;
import com.minlia.module.bible.v1.dao.BibleItemDao;
import com.minlia.module.bible.v1.domain.BibleItem;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 6/17/17.
 */
@Transactional(readOnly = false)
public interface BibleItemWriteOnlyService extends WriteOnlyService<BibleItemDao,BibleItem,Long> {


    /**
     * 根据父CODE创建子项
     * @param code
     * @param body
     * @return
     */
    BibleItem createItem(String code, BibleItem body);

}
