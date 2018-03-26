package com.minlia.module.richtext.mapper;

import com.minlia.module.richtext.body.RichtextQueryRequestBody;
import com.minlia.module.richtext.entity.Richtext;

import java.util.List;

/**
 *
 * @author garen
 */
public interface RichtextMapper {

    int create(Richtext richtext);

    int update(Richtext richtext);

    int delete(Long id);

    long count(RichtextQueryRequestBody requestBody);

    Richtext queryById(Long id);

    Richtext queryOne(RichtextQueryRequestBody requestBody);

    List<Richtext> queryList(RichtextQueryRequestBody requestBody);

    List<Richtext> queryAll();

}
