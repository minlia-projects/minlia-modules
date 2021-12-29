package com.minlia.module.district.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.district.entity.DistrictDto;
import com.minlia.module.district.entity.SysDistrictEntity;
import com.minlia.module.district.enumeration.DistrictLevel;
import com.minlia.module.district.mapper.SysDistrictMapper;
import com.minlia.module.district.service.SysDistrictService;
import com.minlia.module.district.util.XmlUtil;
import com.minlia.module.i18n.enums.LocaleEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public boolean init1() {
        List<SysDistrictEntity> list = this.list(Wrappers.<SysDistrictEntity>lambdaQuery()
                .eq(SysDistrictEntity::getLevel, DistrictLevel.city)
                .eq(SysDistrictEntity::getLocale, LocaleEnum.zh_CN).le(SysDistrictEntity::getId, 45531));
        for (SysDistrictEntity cityEntity : list) {
            if (this.count(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, cityEntity.getAdcode())) > 0) {
                List<SysDistrictEntity> districts = this.list(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, cityEntity.getAdcode()));
                int num = 1;
                for (SysDistrictEntity district : districts) {
                    String newAdcode = NumberGenerator.generator(district.getAdcode(), num++, 2);


                    if (this.count(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, district.getAdcode())) > 0) {
                        List<SysDistrictEntity> streetList = this.list(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, district.getAdcode()));
                        for (SysDistrictEntity districtEntity : streetList) {
                            if (this.count(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, districtEntity.getAdcode())) > 0) {
                                List<SysDistrictEntity> streets = this.list(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, districtEntity.getAdcode()));
                                int numj = 1;
                                for (SysDistrictEntity street : streets) {
                                    street.setParent(newAdcode);
                                    street.setAdcode(NumberGenerator.generator(newAdcode, numj++, 2));
                                    this.updateById(street);
                                }
                            }
                        }
                    }

                    district.setAdcode(newAdcode);
                    this.updateById(district);
                }
            }
        }

//        List<SysDistrictEntity> list = this.list(Wrappers.<SysDistrictEntity>lambdaQuery()
//                .eq(SysDistrictEntity::getLevel, DistrictLevel.district)
//                .eq(SysDistrictEntity::getLocale, LocaleEnum.zh_CN));
//        for (SysDistrictEntity districtEntity : list) {
//            if (this.count(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, districtEntity.getAdcode())) > 0) {
//                List<SysDistrictEntity> streets = this.list(Wrappers.<SysDistrictEntity>lambdaQuery().eq(SysDistrictEntity::getParent, districtEntity.getAdcode()));
//                int num = 1;
//                for (SysDistrictEntity street : streets) {
//                    street.setAdcode(NumberGenerator.generator(street.getAdcode(), num++, 2));
//                    this.updateById(street);
//                }
//            }
//        }
        return true;
    }

}