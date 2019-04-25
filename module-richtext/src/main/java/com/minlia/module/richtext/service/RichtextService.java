package com.minlia.module.richtext.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.richtext.bean.RichtextCTO;
import com.minlia.module.richtext.bean.RichtextQO;
import com.minlia.module.richtext.bean.RichtextUTO;
import com.minlia.module.richtext.entity.Richtext;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 * @author garen
 * @date 2017/06/17
 */
public interface RichtextService {

    Richtext create(RichtextCTO cto);

    Richtext update(RichtextUTO uto);

    void delete(Long id);

    Richtext queryById(Long id);

    Richtext queryByCode(String code);

    long count(RichtextQO qro);

    List<Richtext> queryList(RichtextQO qro);

    PageInfo<Richtext> queryPage(RichtextQO qro, Pageable pageable);

}
