package com.minlia.module.country.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.country.entity.Country;
import com.minlia.module.country.ro.CountryQRO;
import com.minlia.module.country.ro.CountryCRO;
import com.minlia.module.country.ro.CountryURO;
import com.minlia.module.country.mapper.CountryMapper;
import com.minlia.module.country.service.CountryService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public Country create(CountryCRO cto) {
        Country country = mapper.map(cto, Country.class);
        countryMapper.create(country);
        return country;
    }

    @Override
    public Country update(CountryURO uto) {
        Country country = countryMapper.queryById(uto.getId());
        mapper.map(uto,country);
        countryMapper.update(country);
        return country;
    }

    @Override
    public int delete(Long id) {
        return countryMapper.delete(id);
    }

    @Override
    public Country queryById(Long id) {
        return countryMapper.queryById(id);
    }

    @Override
    public List<Country> queryList(CountryQRO qo) {
        return countryMapper.queryList(qo);
    }

    @Override
    public PageInfo<Country> queryPage(CountryQRO qro, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> countryMapper.queryPage(qro));
    }

}
