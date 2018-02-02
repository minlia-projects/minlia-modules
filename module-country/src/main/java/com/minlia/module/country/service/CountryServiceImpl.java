package com.minlia.module.country.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.country.body.CountryCreateRequestBody;
import com.minlia.module.country.body.CountryQueryRequestBody;
import com.minlia.module.country.body.CountryUpdateRequestBody;
import com.minlia.module.country.mapper.CountryMapper;
import com.minlia.module.country.entity.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public Country create(CountryCreateRequestBody requestBody) {
        Country country = mapper.map(requestBody,Country.class);
        countryMapper.create(country);
        return country;
    }

    @Override
    public Country update(CountryUpdateRequestBody requestBody) {
        Country country = countryMapper.queryById(requestBody.getId());
        mapper.map(requestBody,country);
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
    public List<Country> queryList(CountryQueryRequestBody body) {
        return countryMapper.queryList(body);
    }

    @Override
    public PageInfo<Country> queryPage(CountryQueryRequestBody body, RowBounds rowBounds) {
        return countryMapper.queryPage(body);
    }

}
