package com.minlia.module.bank.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.domain.BankcodeDo;
import com.minlia.module.bank.dto.BankcodeQueryDto;
import com.minlia.module.bank.dto.LhhResponse;
import com.minlia.module.bank.mapper.BankcodeMapper;
import com.minlia.module.map.district.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.district.entity.GadDistrict;
import com.minlia.module.map.district.service.GadDistrictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by user on 3/13/17.
 */
@Service
@Slf4j
public class BankcodeServiceImpl implements BankcodeService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankcodeMapper bankcodeMapper;

    @Autowired
    private GadDistrictService gadDistrictService;

    private static String appcode = "APPCODE 6889a6bedf53468ea27d10f12a8e5159";

    @Override
    public void init() {
//        Map<String, Object> querys = new HashMap<String, Object>();
//        querys.put("bankcard", "bankcard");
//        querys.put("bankname", "bankname");
//        querys.put("province", "province");
//        querys.put("city", "city");
//        querys.put("district", "district");
//        querys.put("keyword", "keyword");
//        querys.put("page", page);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", appcode);
        HttpEntity httpEntity = new HttpEntity(null,headers);

        List<GadDistrict> provinces = gadDistrictService.queryList(GadDistrictQueryRequestBody.builder().level("province").build());
        for (GadDistrict province : provinces) {
            List<GadDistrict> citys = gadDistrictService.queryList(GadDistrictQueryRequestBody.builder().parent(province.getAdcode()).build());
            for (GadDistrict city : citys) {
                List<GadDistrict> districts = gadDistrictService.queryList(GadDistrictQueryRequestBody.builder().parent(city.getAdcode()).build());
                for (GadDistrict district : districts) {
                    System.out.println(new StringJoiner("-").add(province.getName()).add(city.getName()).add(district.getName()).toString());

                    Map<String, Object> querys = new HashMap<String, Object>();
                    long count;
                    if (province.getName().contains("北京") || province.getName().contains("上海") || province.getName().contains("天津")|| province.getName().contains("重庆")) {
                        count = bankcodeMapper.count(BankcodeQueryDto.builder().province(province.getName()).district(district.getName()).build());
                        querys.put("city", province.getName());
                    } else {
                        count = bankcodeMapper.count(BankcodeQueryDto.builder().province(province.getName()).city(city.getName()).district(district.getName()).build());
                        querys.put("city", city.getName());
                    }
                    querys.put("province", province.getName());
                    querys.put("district", district.getName());
                    querys.put("bankname", "中国农业银行");

                    if (count < 200) {
                        init(1,httpEntity,querys);
                    }
                }
            }
        }

//        HttpResponse<String> response = Unirest.get("http://lhh.market.alicloudapi.com/lhh")
//                .header("Authorization", "APPCODE " + appcode)
//                .queryString(querys)
//                .asString();
    }

    private void init(int page, HttpEntity httpEntity, Map querys){
        querys.put("page", page);
        ResponseEntity<LhhResponse> response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page={page}&province={province}&city={city}&district={district}&bankname={bankname}", HttpMethod.GET, httpEntity, LhhResponse.class,querys);
        if (response.getBody().isSuccess()) {
            log.warn("获取联行号当前页数--------------:{}",response.getBody().getResult().getPaging().getPageNow());
            if (CollectionUtils.isNotEmpty(response.getBody().getResult().getList())) {
                for (BankcodeDo bankcodeDo : response.getBody().getResult().getList()) {
                    this.create(bankcodeDo);
                }
            }

            if (page < response.getBody().getResult().getPaging().getPageTotal()) {
                this.init(++page,httpEntity,querys);
            }
        } else {
            log.warn("获取联行号失败:{}",response.getBody().getErrorCode());
        }
    }

    @Override
    public BankcodeDo create(BankcodeDo bankCard) {
        try {
            bankcodeMapper.create(bankCard);
        } catch (Exception e) {
            log.error("联行号已存在：{}-{}",bankCard.getNumber(),e.getMessage());
        }
        return bankCard;
    }

    @Override
    public BankcodeDo update(BankcodeDo bankCard) {
        bankcodeMapper.update(bankCard);
        return bankCard;
    }

    @Override
    public void delete(String number) {
        bankcodeMapper.delete(number);
    }

    @Override
    public BankcodeDo queryByNumber(String number) {
        return bankcodeMapper.queryByNumber(number);
    }

    @Override
    public BankcodeDo queryOne(BankcodeQueryDto dto) {
        return bankcodeMapper.queryOne(dto);
    }

    @Override
    public List<BankcodeDo> queryList(BankcodeQueryDto dto) {
        return bankcodeMapper.queryList(dto);
    }

    @Override
    public PageInfo<BankcodeDo> queryPage(BankcodeQueryDto dto, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()->bankcodeMapper.queryList(dto));
    }

}
