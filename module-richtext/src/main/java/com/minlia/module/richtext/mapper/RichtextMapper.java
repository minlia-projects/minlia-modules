package com.minlia.module.richtext.mapper;

import com.minlia.module.richtext.bean.RichtextQRO;
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

    Richtext queryById(Long id);

    Richtext queryByCode(String code);

    long count(RichtextQRO qro);

    Richtext queryOne(RichtextQRO qro);

    List<Richtext> queryList(RichtextQRO qro);

}
