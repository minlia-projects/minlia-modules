//package com.minlia.module.map.tencent.service;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.boot.v1.body.impl.SuccessResponseBody;
//import com.minlia.module.map.tencent.body.TencentDistrictQueryRequestBody;
//import com.minlia.module.map.tencent.constants.TencentConstants;
//import com.minlia.module.map.tencent.domain.TencentDistrict;
//import com.minlia.module.map.tencent.repository.TencentDistrictRepository;
//import com.minlia.module.security.v1.utils.SecurityUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Calvin On 2017/12/14.
// */
//@Service
//@Slf4j
//public class TencentDistrictServiceImpl implements TencentDistrictService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private TencentDistrictRepository repository;
//
//    @Override
//    public StatefulBody initAllDstrict() {
//
//        return SuccessResponseBody.builder().code(1).message("初始化完成").build();
//    }
//
//    @Override
//    public List<TencentDistrict> findAllByParentCode(String parentCode) {
//        return repository.findAllByParentCode(parentCode);
//    }
//
//    @Override
//    public List<TencentDistrict> findList(TencentDistrictQueryRequestBody requestBody) {
//        return repository.findAll(specBuilder(requestBody));
//    }
//
//    @Override
//    public Page<TencentDistrict> findPage(TencentDistrictQueryRequestBody requestBody, Pageable pageable) {
//        return repository.findAll(specBuilder(requestBody), pageable);
//    }
//
//    private Specification<TencentDistrict> specBuilder(TencentDistrictQueryRequestBody requestBody){
//        Specification<TencentDistrict> spec = new Specification<TencentDistrict>() {
//            public Predicate toPredicate(Root<TencentDistrict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> list = new ArrayList<Predicate>();
//
//                if(null == requestBody.getCreatedBy()){
//                    Long userId = SecurityUtils.getCurrentUser().getId();
//                    if(!userId.equals(1l)){
//                        requestBody.setCreatedBy(userId);
//                    }
//                }
//
//               if(null != requestBody.getCode())
//                   list.add(cb.equal(root.get("code").as(String.class), requestBody.getCode()));
//
//               if(null != requestBody.getFullCode())
//                   list.add(cb.equal(root.get("fullcode").as(String.class), requestBody.getFullCode()));
//
//               if(null != requestBody.getName())
//                    list.add(cb.equal(root.get("name").as(String.class), requestBody.getName()));
//
//               if(null != requestBody.getFullname())
//                    list.add(cb.equal(root.get("fullname").as(String.class), requestBody.getFullname()));
//
//               if(null != requestBody.getPinyin())
//                    list.add(cb.equal(root.get("pinyin").as(String.class), requestBody.getPinyin()));
//
//               if(null != requestBody.getAddress())
//                    list.add(cb.like(root.get("address").as(String.class), "%"+requestBody.getAddress()+"%"));
//
//               if(null != requestBody.getLatitude() && null != requestBody.getLongitude())
//                   list.add(cb.and((cb.equal(root.get("latitude").as(Double.class), requestBody.getLatitude())),(cb.equal(root.get("longitude").as(Double.class),requestBody.getLongitude()))));
//
//               if(null != requestBody.getLevel())
//                    list.add(cb.equal(root.get("level").as(Integer.class), requestBody.getLevel()));
//
//               query.orderBy(cb.desc(root.get("id").as(Long.class)));
//
//               Predicate[] p = new Predicate[list.size()];
//               return cb.and(list.toArray(p));
//            }
//        };
//        return spec;
//    }
//
//    private void getTencentQQMap(){
//        //获取所有区域结果集（省、市、区）
//        Map<String, Object> mapResultSet = restTemplate.getForObject(String.format(TencentConstants.QQ_MAP_URL_LIST, TencentConstants.QQ_MAP_KEYS[19]), Map.class);
//
//        //获取(key=result value 中的所有集合[0]:代表省、[1]代表市、[2]代表区）
//        List<Map<String,Object>> districts = (List<Map<String, Object>>) mapResultSet.get("result");
//
//        //获取省份
//        List<Map<String,Object>> provinces = (List<Map<String, Object>>) districts.get(0);
//
//        //保存省份
//        saveTencentQQMap(provinces, 1, "","","");
//
//        try {
//            getQQMapChildren(provinces,1,"","","");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getQQMapChildren(List<Map<String,Object>> parents, Integer level,String parentCode,String parentFullCode,String parentAddress) throws InterruptedException {
//        int key_index = 3;
//
//        //行政区域编号长度为6
//        if (parentCode.length() > 6)
//            return;
//
//        for (Map<String,Object> parent:parents) {
//            //行政区域编号长度为6
//            if (parent.get("id").toString().length() > 6)
//                continue;
//
//            Thread.sleep(5000);
//            //根据省份集id,获取市的结果集
//            Map<String, Object> childrentMap = restTemplate.getForObject(String.format(TencentConstants.QQ_MAP_URL_GET_CHILDREN, parent.get("id"), TencentConstants.QQ_MAP_KEYS[key_index]), Map.class);
//
//            //判断市集状态是否为0
//            if (childrentMap.get("status").equals(0)) {
//                //获取市集下的values 值
//                List<Map<String, Object>> children = (List<Map<String, Object>>) ((List<Map<String, Object>>) childrentMap.get("result")).get(0);
//
//                //遍历保存市集下value值
//                saveTencentQQMap(children,
//                        level + 1,
//                        (String) parent.get("id"),
//                        (StringUtils.isEmpty(parentFullCode) ? "":parentFullCode+ ",") + parent.get("id"),
//                        parentAddress + (String) parent.get("fullname"));
//
//                //递归（区，县）
//                getQQMapChildren(children,
//                        level + 1,
//                        (String) parent.get("id"),
//                        (StringUtils.isEmpty(parentFullCode) ? "":parentFullCode+ ",") + parent.get("id"),
//                        parentAddress + (String) parent.get("fullname"));
//
//            } else if(childrentMap.get("status").equals(121)){
//                key_index++;
//                //递归（区，县）
//                getQQMapChildren(parents,
//                        level,
//                        (String) parent.get("id"),
//                        (StringUtils.isEmpty(parentFullCode) ? "":parentFullCode+ ",") + parent.get("id"),
//                        parentAddress + (String) parent.get("fullname"));
//            } else {
//                System.out.println(parentAddress +"-"+ parent.get("id") + ":" + childrentMap.get("message"));
//            }
//        }
//    }
//
//    @Transactional(propagation = Propagation.NEVER)
//    public void saveTencentQQMap(List<Map<String,Object>> args, Integer level,String parentCode,String parentFullCode, String parentAddress){
//        for(Map<String, Object> arg: args){
//            try {
//                String code = (String)arg.get("id");
//                String name = (String) arg.get("name");
//                String fullname = (String) arg.get("fullname");
//
//                List<String> pinyins = (List<String>) arg.get("pinyin");
//                String pinyin = pinyins == pinyins ? null : String.join(" ",pinyins);
//
//                Map<String,Double> location = (Map<String, Double>) arg.get("location");
//                Double longitude = null == location ? null : location.get("lng");
//                Double latitude = null == location ? null : location.get("lat");
//
//                String cidx = (String) arg.get("cidx").toString();
//
//                if (repository.countByCode(code) == 0) {
//                    TencentDistrict district = TencentDistrict.builder().code(code)
//                            .parentCode(parentCode)
//                            .fullcode(StringUtils.isEmpty(parentFullCode) ? "" : (parentFullCode + ",") + code)
//                            .address(parentAddress + fullname)
//                            .fullname(fullname)
//                            .name(name)
//                            .pinyin(pinyin)
//                            .level(level)
//                            .longitude(longitude)
//                            .latitude(latitude)
//                            .cidxs(cidx)
//                            .build();
//                    repository.save(district);
//                }
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}