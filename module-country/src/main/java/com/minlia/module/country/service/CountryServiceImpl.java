package com.minlia.module.country.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.country.bean.domain.Country;
import com.minlia.module.country.bean.qo.CountryQO;
import com.minlia.module.country.bean.to.CountryCTO;
import com.minlia.module.country.bean.to.CountryUTO;
import com.minlia.module.country.mapper.CountryMapper;
import lombok.extern.slf4j.Slf4j;
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
    public Country create(CountryCTO cto) {
        Country country = mapper.map(cto, Country.class);
        countryMapper.create(country);
        return country;
    }

    @Override
    public Country update(CountryUTO uto) {
        Country country = countryMapper.queryById(uto.getId());
        mapper.map(uto,country);
        countryMapper.update(country);
        return country;
    }

    @Override
    public void delete(Long id) {
        countryMapper.delete(id);
    }

    @Override
    public Country queryById(Long id) {
        return countryMapper.queryById(id);
    }

    @Override
    public List<Country> queryList(CountryQO qo) {
        return countryMapper.queryList(qo);
    }

    @Override
    public PageInfo<Country> queryPage(CountryQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(() -> countryMapper.queryPage(qo));
    }

}
