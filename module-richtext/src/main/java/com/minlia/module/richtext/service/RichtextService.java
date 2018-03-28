package com.minlia.module.richtext.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.richtext.body.RichtextCreateRequestBody;
import com.minlia.module.richtext.body.RichtextQueryRequestBody;
import com.minlia.module.richtext.body.RichtextUpdateRequestBody;
import com.minlia.module.richtext.entity.Richtext;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 * @author garen
 * @date 2017/06/17
 */
public interface RichtextService {

    Richtext create(RichtextCreateRequestBody requestBody);

    Richtext update(RichtextUpdateRequestBody requestBody);

    void delete(Long id);

    long count(RichtextQueryRequestBody requestBody);

    Richtext queryById(Long id);

    Richtext queryByCode(String code);

    List<Richtext> queryList(RichtextQueryRequestBody requestBody);

    PageInfo<Richtext> queryPage(RichtextQueryRequestBody requestBody, Pageable pageable);

}
