package com.minlia.module.disrtict.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.disrtict.bean.domain.District;
import com.minlia.module.disrtict.bean.qo.DistrictQO;
import com.minlia.module.disrtict.enumeration.DistrictLevel;
import com.minlia.module.disrtict.service.DistrictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Calvin On 2017/12/14.
 */
@Api(tags = "Gad district", description = "行政区域")
@RestController
@RequestMapping(value = ApiPrefix.API + "gad/district")
public class DistrictEndpoint {

    @Autowired
    private DistrictService service;

//    @PreAuthorize(value = "hasAnyAuthority('"+ DistrictConstants.ENTITY_CREATE +"')")
//    @ApiOperation(value = "区域初始化", notes = "区域初始化(省、市、区、县)", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping(value = "init", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response init() {
//       return service.initAllDstrict();
//    }

    @ApiOperation(value = "查询级别", notes = "查询级别", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "level/{level}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryAllByParentCode(@PathVariable DistrictLevel level) {
        List<District> districtList = service.queryList(DistrictQO.builder().level(level.name()).build());
        return Response.success(districtList);
    }

    @ApiOperation(value = "查询子级", notes = "查询子级", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "children", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public Response queryAllByParentCode(@RequestParam(required = false) String parent) {
        List<District> districtList;
        if (StringUtils.isBlank(parent)) {
            districtList = service.queryList(DistrictQO.builder().level(DistrictLevel.province.name()).build());
        } else {
            districtList = service.queryList(DistrictQO.builder().parent(parent).build());
        }
        return Response.success(districtList);
    }

    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody DistrictQO qo) {
        List<District> districtList = service.queryList(qo);
        return Response.success(districtList);
    }

//    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "page", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response paginated(@PageableDefault Pageable pageable, @RequestBody DistrictQO qo) {
//        PageInfo<District> pageInfo = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), qo.getOrderBy()).doSelectPageInfo(()-> service.queryList(qo));
//        return Response.success(pageInfo);
//    }

}
