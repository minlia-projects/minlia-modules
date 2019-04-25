package com.minlia.module.bankbranch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bankbranch.entity.BankBranch;
import com.minlia.module.bankbranch.ro.BankBranchQRO;
import com.minlia.module.bankbranch.mapper.BankBranchMapper;
import com.minlia.module.bankbranch.service.BankBranchService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by user on 3/13/17.
 */
@Service
@Slf4j
public class BankBranchServiceImpl implements BankBranchService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankBranchMapper bankBranchMapper;

//    @Autowired
//    private DistrictService districtService;
//
//    private static String appcode = "APPCODE dsafdsafds";
//
//    @Override
//    public void init() {
////        Map<String, Object> querys = new HashMap<String, Object>();
////        querys.put("bankcard", "bankcard");
////        querys.put("bankname", "bankname");
////        querys.put("province", "province");
////        querys.put("city", "city");
////        querys.put("district", "district");
////        querys.put("keyword", "keyword");
////        querys.put("page", page);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", appcode);
//        HttpEntity httpEntity = new HttpEntity(null,headers);
//
//        List<String> banks = Lists.newArrayList(
//                "农村信用联社"
//        );
//
//        for (String bank : banks) {
//            List<District> provinces = districtService.queryList(DistrictQO.builder().level("province").build());
//            for (District province : provinces) {
//                List<District> citys = districtService.queryList(DistrictQO.builder().parent(province.getAdcode()).build());
//                for (District city : citys) {
//                    List<District> districts = districtService.queryList(DistrictQO.builder().parent(city.getAdcode()).build());
//                    for (District district : districts) {
//                        System.out.println(new StringJoiner("-").add(province.getName()).add(city.getName()).add(district.getName()).toString());
//
//                        Map<String, Object> querys = new HashMap<String, Object>();
//                        if (province.getName().contains("北京") || province.getName().contains("上海") || province.getName().contains("天津") || province.getName().contains("重庆")) {
//                            querys.put("city", province.getName());
//                        } else {
//                            querys.put("city", city.getName());
//                        }
//                        querys.put("bankname", bank);
//                        querys.put("province", province.getName());
//                        querys.put("district", district.getName());
//
//                        init(1, httpEntity, querys);
//                    }
////                    System.out.println(new StringJoiner("-").add(province.getName()).add(city.getName()).toString());
////                    Map<String, String> querys = new HashMap<String, String>();
////                    if (province.getName().contains("北京") || province.getName().contains("上海") || province.getName().contains("天津") || province.getName().contains("重庆")) {
////                        querys.put("city", province.getName());
////                    } else {
////                        querys.put("city", city.getName());
////                    }
////                    querys.put("bankname", bank);
////                    querys.put("province", province.getName());
////
////                    init(1, httpEntity, querys);
//                }
//            }
//        }
//
//
//
////        HttpResponse<String> response = Unirest.get("http://lhh.market.alicloudapi.com/lhh")
////                .header("Authorization", "APPCODE " + appcode)
////                .queryString(querys)
////                .asString();
//    }
//
//    private void init(int page, HttpEntity httpEntity, Map querys){
//        querys.put("page", page);
//        ResponseEntity<LhhResponse> response;
//        if (null != querys.get("district")) {
//            response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page={page}&province={province}&city={city}&district={district}&bankname={bankname}", HttpMethod.GET, httpEntity, LhhResponse.class,querys);
//        } else if (null != querys.get("city")) {
//            response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page={page}&province={province}&city={city}&bankname={bankname}", HttpMethod.GET, httpEntity, LhhResponse.class,querys);
//        } else if (null != querys.get("province")) {
//            response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page={page}&province={province}&bankname={bankname}", HttpMethod.GET, httpEntity, LhhResponse.class,querys);
//        } else {
//            response = restTemplate.exchange("http://lhh.market.alicloudapi.com/lhh?page={page}&bankname={bankname}", HttpMethod.GET, httpEntity, LhhResponse.class,querys);
//        }
//        if (response.getBody().isSuccess()) {
//            log.warn("获取联行号当前页数--------------:{}",response.getBody().getResult().getPaging().getPageNow());
//            if (CollectionUtils.isNotEmpty(response.getBody().getResult().getList())) {
//                for (BankBranch bankBranchDo : response.getBody().getResult().getList()) {
//                    this.create(bankBranchDo);
//                }
//            }
//
//            if (page < response.getBody().getResult().getPaging().getPageTotal()) {
//                this.init(++page,httpEntity,querys);
//            }
//        } else {
//            log.warn("获取联行号失败:{}",response.getBody().getErrorCode());
//        }
//    }

    @Override
    public BankBranch create(BankBranch bankCard) {
        try {
            bankBranchMapper.create(bankCard);
        } catch (Exception e) {
            log.error("联行号已存在：{}-{}",bankCard.getNumber(),e.getMessage());
        }
        return bankCard;
    }

    @Override
    public BankBranch update(BankBranch bankCard) {
        bankBranchMapper.update(bankCard);
        return bankCard;
    }

    @Override
    public void delete(String number) {
        bankBranchMapper.delete(number);
    }

    @Override
    public boolean exists(String number) {
        return bankBranchMapper.count(BankBranchQRO.builder().number(number).build()) > 0;
    }

    @Override
    public BankBranch queryByNumber(String number) {
        return bankBranchMapper.queryByNumber(number);
    }

    @Override
    public List<BankBranch> queryList(BankBranchQRO qo) {
        return bankBranchMapper.queryList(qo);
    }

    @Override
    public PageInfo<BankBranch> queryPage(BankBranchQRO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()-> bankBranchMapper.queryList(qo));
    }

}
