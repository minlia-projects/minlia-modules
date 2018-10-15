package com.minlia.module.bible.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.bean.to.BibleCTO;
import com.minlia.module.bible.bean.qo.BibleQO;
import com.minlia.module.bible.bean.to.BibleUTO;
import com.minlia.module.bible.bean.domain.Bible;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
public interface BibleService {

    Bible create(BibleCTO cto);

    Bible update(BibleUTO uto);

    void delete(Long id);

    void initialBibleWithCode(String bibleCode,String code,String label,String notes,String attribute1);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQO qo);

    PageInfo<Bible> queryPage(BibleQO qo, Pageable pageable);

}
