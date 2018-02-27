//package com.minlia.module.map.tencent.endpoint;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.boot.v1.body.impl.SuccessResponseBody;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.data.query.v2.body.ApiSearchRequestBody;
//import com.minlia.module.map.tencent.body.TencentDistrictQueryRequestBody;
//import com.minlia.module.map.tencent.constants.TencentDistrictSecurityContants;
//import com.minlia.module.map.tencent.domain.TencentDistrict;
//import com.minlia.module.map.tencent.service.TencentDistrictService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * Created by Calvin On 2017/12/14.
// */
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "district/search")
//@Api(tags = "地图区域", description = "地图区域")
//@Slf4j
//public class TencentMapDistrictSearchEndpoint {
//
//    @Autowired
//    private TencentDistrictService service;
//
//
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ TencentDistrictSecurityContants.ENTITY_SEARCH +"')")
//    @ApiOperation(value = "父类CODE查询", notes = "根据父类CODE查询行政区域", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
////    @GetMapping(value = "{parentCode}", produces = {MediaType.APPLICATION_JSON_VALUE} )
//    public StatefulBody findAllByParentCode(@PathVariable String parentCode) {
//        List<TencentDistrict> districtList = service.findAllByParentCode(parentCode);
//        return SuccessResponseBody.builder().payload(districtList).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+ TencentDistrictSecurityContants.ENTITY_SEARCH +"')")
//    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
////    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody list(@RequestBody ApiSearchRequestBody<TencentDistrictQueryRequestBody> body) {
//        List<TencentDistrict> districtList = service.findList(body.getPayload());
//        return SuccessResponseBody.builder().payload(districtList).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('"+  TencentDistrictSecurityContants.ENTITY_SEARCH +"')")
//    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
////    @RequestMapping(value = "paginated", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<TencentDistrictQueryRequestBody> body) {
//        Page page = service.findPage(body.getPayload(),pageable);
//        return SuccessResponseBody.builder().payload(page).build();
//    }
//}
