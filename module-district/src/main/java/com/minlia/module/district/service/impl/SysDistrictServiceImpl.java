package com.minlia.module.district.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.district.entity.DistrictDto;
import com.minlia.module.district.entity.SysDistrictEntity;
import com.minlia.module.district.enumeration.DistrictLevel;
import com.minlia.module.district.mapper.SysDistrictMapper;
import com.minlia.module.district.service.SysDistrictService;
import com.minlia.module.district.util.XmlUtil;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 区域 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-04-06
 */
@Service
public class SysDistrictServiceImpl extends ServiceImpl<SysDistrictMapper, SysDistrictEntity> implements SysDistrictService {

    @Override
    public boolean init() {
        Object districtDto = XmlUtil.convertXmlFileToObject(DistrictDto.class, "/Users/garen/Documents/cameslc/区域地址/zh.xml");
        DistrictDto dto = (DistrictDto) districtDto;
        //国家
        for (DistrictDto.Country country : dto.getCountries()) {
            SysDistrictEntity countryEntity = SysDistrictEntity.builder()
                    .parent(null).adcode(country.getCode()).name(country.getName()).level(DistrictLevel.country).locale(LocaleEnum.zh_CN)
                    .fullcode(country.getCode()).address(country.getName()).build();
            this.save(countryEntity);
            //省、州
            if (CollectionUtils.isEmpty(country.getStates())) continue;
            for (DistrictDto.Country.State state : country.getStates()) {
                if (Objects.nonNull(state.getCode())) {
                    SysDistrictEntity stateEntity = SysDistrictEntity.builder()
                            .parent(country.getCode()).adcode(state.getCode()).name(state.getName()).level(DistrictLevel.province).locale(LocaleEnum.zh_CN)
                            .fullcode(country.getCode() + "," + state.getCode()).address(country.getName() + " " + state.getName()).build();
                    this.save(stateEntity);
                    //城市
                    if (CollectionUtils.isEmpty(state.getCities())) continue;
                    for (DistrictDto.Country.State.City city : state.getCities()) {
                        SysDistrictEntity cityEntity = SysDistrictEntity.builder()
                                .parent(state.getCode()).adcode(city.getCode()).name(city.getName()).level(DistrictLevel.city).locale(LocaleEnum.zh_CN)
                                .fullcode(country.getCode() + "," + state.getCode() + "," + city.getCode()).address(country.getName() + " " + state.getName() + " " + city.getName()).build();
                        this.save(cityEntity);
                    }
                } else {
                    //城市
                    if (CollectionUtils.isEmpty(state.getCities())) continue;
                    for (DistrictDto.Country.State.City city : state.getCities()) {
                        SysDistrictEntity cityEntity = SysDistrictEntity.builder()
                                .parent(country.getCode()).adcode(city.getCode()).name(city.getName()).level(DistrictLevel.city).locale(LocaleEnum.zh_CN)
                                .fullcode(country.getCode() + "," + city.getCode()).address(country.getName() + " " + city.getName()).build();
                        this.save(cityEntity);
                    }
                }
            }
        }
        return true;
    }

}