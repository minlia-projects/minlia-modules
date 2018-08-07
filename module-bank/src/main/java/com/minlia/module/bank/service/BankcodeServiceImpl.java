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
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private BankcodeMapper bankCardMapper;

    @Autowired
    private GadDistrictService gadDistrictService;

    private static String appcode = "6889a6bedf53468ea27d10f12a8e5159";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void init(int page) {
//        Map<String, Object> querys = new HashMap<String, Object>();
//        querys.put("bankcard", "bankcard");
//        querys.put("bankname", "bankname");
//        querys.put("city", "city");
//        querys.put("district", "district");
//        querys.put("keyword", "keyword");
//        querys.put("page", page);
//        querys.put("province", "province");

//        List<GadDistrict> provinces = gadDistrictService.queryList(GadDistrictQueryRequestBody.builder().level("province").build());
//        for (GadDistrict province : provinces) {
//            List<GadDistrict> citys = gadDistrictService.queryList(GadDistrictQueryRequestBody.builder().parent(province.getAdcode()).build());
//            for (GadDistrict city : citys) {
//                List<GadDistrict> districts = gadDistrictService.queryList(GadDistrictQueryRequestBody.builder().parent(city.getAdcode()).build());
//                for (GadDistrict district : districts) {
//                    System.out.println(province.getName() + "-" + city.getName() + "-" + district.getName());
//
//
//                    Map<String, Object> querys = new HashMap<String, Object>();
//                    querys.put("province", "province");
//                    querys.put("city", "city");
//                    querys.put("district", "district");
//                    querys.put("keyword", "keyword");
//                    querys.put("page", page);
//
//
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.add("Authorization", "APPCODE " + appcode);
//                    HttpEntity httpEntity = new HttpEntity(null,headers);
//                    ResponseEntity<LhhResponse> response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page=1", HttpMethod.GET, httpEntity, LhhResponse.class);
//
//                    if (response.getBody().isSuccess()) {
//                        log.warn("获取联行号当前页数--------------:{}",response.getBody().getResult().getPaging().getPageNow());
//                        for (BankcodeDo bankcodeDo : response.getBody().getResult().getList()) {
//                            this.create(bankcodeDo);
//                        }
//                    } else {
//                        log.warn("获取联行号失败:{}",response.getBody().getErrorCode());
//                    }
//                }
//            }
//        }

        Map<String, Object> querys = new HashMap<String, Object>();
        querys.put("province", "广东省");
        querys.put("city", "深圳市");
        querys.put("district", "宝安区");
        querys.put("page", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "APPCODE " + appcode);
        HttpEntity httpEntity = new HttpEntity(null,headers);
        ResponseEntity<LhhResponse> response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page={page}&province={province}&city={city}&district={district}", HttpMethod.GET, httpEntity, LhhResponse.class,querys);

        if (response.getBody().isSuccess()) {
            log.warn("获取联行号当前页数--------------:{}",response.getBody().getResult().getPaging().getPageNow());
            for (BankcodeDo bankcodeDo : response.getBody().getResult().getList()) {
                this.create(bankcodeDo);
            }
        } else {
            log.warn("获取联行号失败:{}",response.getBody().getErrorCode());
        }

//        HttpResponse<String> response = Unirest.get("http://lhh.market.alicloudapi.com/lhh")
//                .header("Authorization", "APPCODE " + appcode)
//                .queryString(querys)
//                .asString();
    }


    @Override
    public BankcodeDo create(BankcodeDo bankCard) {
        bankCardMapper.create(bankCard);
        return bankCard;
    }

    @Override
    public BankcodeDo update(BankcodeDo bankCard) {
        bankCardMapper.update(bankCard);
        return bankCard;
    }

    @Override
    public void delete(String number) {
        bankCardMapper.delete(number);
    }

    @Override
    public BankcodeDo queryByNumber(String number) {
        return bankCardMapper.queryByNumber(number);
    }

    @Override
    public BankcodeDo queryOne(BankcodeQueryDto dto) {
        return bankCardMapper.queryOne(dto);
    }

    @Override
    public List<BankcodeDo> queryList(BankcodeQueryDto dto) {
        return bankCardMapper.queryList(dto);
    }

    @Override
    public PageInfo<BankcodeDo> queryPage(BankcodeQueryDto dto, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageSize(),pageable.getPageNumber()).doSelectPageInfo(()->bankCardMapper.queryList(dto));
    }

}
