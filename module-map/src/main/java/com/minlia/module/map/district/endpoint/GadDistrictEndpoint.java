package com.minlia.module.map.district.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.map.district.entity.GadDistrict;
import com.minlia.module.map.district.body.GadDistrictQueryRequestBody;
import com.minlia.module.map.district.enumeration.DistrictLevel;
import com.minlia.module.map.district.service.GadDistrictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Calvin On 2017/12/14.
 */
@RestController
@RequestMapping(value = ApiPrefix.API + "gad/district")
@Api(tags = "Gad district", description = "行政区域")
@Slf4j
public class GadDistrictEndpoint {

    @Autowired
    private GadDistrictService service;

//    @PreAuthorize(value = "hasAnyAuthority('"+ DistrictSecurityConstants.ENTITY_CREATE +"')")
//    @ApiOperation(value = "区域初始化", notes = "区域初始化(省、市、区、县)", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping(value = "init", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody init() {
//       return service.initAllDstrict();
//    }

    @ApiOperation(value = "查询级别", notes = "查询级别", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "query/level/{level}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryAllByParentCode(@PathVariable DistrictLevel level) {
        List<GadDistrict> districtList = service.queryList(GadDistrictQueryRequestBody.builder().level(level.name()).build());
        return SuccessResponseBody.builder().payload(districtList).build();
    }

    @ApiOperation(value = "查询子级", notes = "查询子级", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "query/children/{parentcode}", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public StatefulBody queryAllByParentCode(@PathVariable String parentcode) {
        List<GadDistrict> districtList = service.queryList(GadDistrictQueryRequestBody.builder().parent(parentcode).build());
        return SuccessResponseBody.builder().payload(districtList).build();
    }

    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "query/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@RequestBody GadDistrictQueryRequestBody requestBody) {
        List<GadDistrict> districtList = service.queryList(requestBody);
        return SuccessResponseBody.builder().payload(districtList).build();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "query/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody GadDistrictQueryRequestBody requestBody) {
        PageInfo<GadDistrict> pageInfo = PageHelper.startPage(pageable.getOffset(), pageable.getPageSize()).doSelectPageInfo(()-> service.queryList(requestBody));
        return SuccessResponseBody.builder().payload(pageInfo).build();
    }

}
