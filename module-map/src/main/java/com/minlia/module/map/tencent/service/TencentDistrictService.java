//package com.minlia.module.map.tencent.service;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.module.map.tencent.body.TencentDistrictQueryRequestBody;
//import com.minlia.module.map.tencent.domain.TencentDistrict;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
///**
// * Created by Calvin On 2017/12/14.
// */
//public interface TencentDistrictService  {
//
//
//    /**
//     * 获取所有区域
//     * @return
//     */
//    StatefulBody initAllDstrict();
//
//    List<TencentDistrict> findAllByParentCode(String parentCode);
//
//    List<TencentDistrict> findList(TencentDistrictQueryRequestBody requestBody);
//
//    Page<TencentDistrict> findPage(TencentDistrictQueryRequestBody requestBody, Pageable pageable);
//
//}
