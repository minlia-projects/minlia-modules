package com.minlia.module.richtext.v1.service;


import com.minlia.module.richtext.v1.body.RichtextCreateBody;
import com.minlia.module.richtext.v1.body.RichtextQueryBody;
import com.minlia.module.richtext.v1.body.RichtextUpdateBody;
import com.minlia.module.richtext.v1.domain.Richtext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
@Transactional
public interface RichtextService {

    Richtext create(RichtextCreateBody body);

    Richtext update(RichtextUpdateBody body);

    void delete(Long id);

    long count(RichtextQueryBody body);

    Richtext findOne(RichtextQueryBody body);

    List<Richtext> findList(RichtextQueryBody body);

    Page<Richtext> findPage(RichtextQueryBody body, Pageable pageable);

}
