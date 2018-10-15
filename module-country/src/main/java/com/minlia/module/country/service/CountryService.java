package com.minlia.module.country.service;


import com.github.pagehelper.PageInfo;
import com.minlia.module.country.bean.domain.Country;
import com.minlia.module.country.bean.qo.CountryQO;
import com.minlia.module.country.bean.to.CountryCTO;
import com.minlia.module.country.bean.to.CountryUTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by will on 6/17/17.
 */
@Transactional
public interface CountryService {

    Country create(CountryCTO cto);

    Country update(CountryUTO uto);

    void delete(Long id);

    Country queryById(Long id);

    List<Country> queryList(CountryQO qo);

    PageInfo<Country> queryPage(CountryQO qo, Pageable pageable);

}
