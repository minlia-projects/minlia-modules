package com.minlia.module.bible.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bible.body.BibleQueryRequestBody;
import com.minlia.module.bible.entity.Bible;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 8/22/17.
 */
@Component
public interface BibleMapper {

    void create(Bible bible);

    void update(Bible bible);

    void delete(Long id);

    Bible queryById(Long id);

    Bible queryByCode(String code);

    List<Bible> queryList(BibleQueryRequestBody body);

    PageInfo<Bible> queryPaginated(BibleQueryRequestBody body, Page page);

}
