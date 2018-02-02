package com.minlia.module.country.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.country.body.CountryCreateRequestBody;
import com.minlia.module.country.body.CountryQueryRequestBody;
import com.minlia.module.country.body.CountryUpdateRequestBody;
import com.minlia.module.country.entity.Country;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
@Transactional
public interface CountryService {

    Country create(CountryCreateRequestBody requestBody);

    Country update(CountryUpdateRequestBody requestBody);

    void delete(Long id);

    Country queryById(Long id);

    List<Country> queryList(CountryQueryRequestBody body);

    PageInfo<Country> queryPage(CountryQueryRequestBody body, RowBounds rowBounds);

}
