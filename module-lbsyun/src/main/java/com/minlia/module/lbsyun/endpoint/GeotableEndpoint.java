package com.minlia.module.lbsyun.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.lbsyun.body.request.GeocolumnCreateRequest;
import com.minlia.module.lbsyun.body.request.GeotableCreateRequest;
import com.minlia.module.lbsyun.service.GeotableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by garen On 2017/12/14.
 * @author garen
 */
@Api(tags = "Lbsyun Geo", description = "百度云存储")
@RestController
@RequestMapping(value = ApiPrefix.API + "lbs/geo")
public class GeotableEndpoint {

    @Autowired
    private GeotableService geotableService;

    @ApiOperation(value = "创建表", notes = "创建表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "table/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response createTable(@Valid @RequestBody GeotableCreateRequest request) {
        return Response.success(geotableService.create(request));
    }

    @ApiOperation(value = "创建列", notes = "创建列", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "cloumn/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response createColumn(@Valid @RequestBody GeocolumnCreateRequest request) {
        return Response.success(geotableService.createColumn(request));
    }

//    @ApiOperation(value = "update data test", notes = "更新数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "updateData", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response updateData() {
//        yuntuService.updateData(null);
//        return SuccessResponseBody.builder();
//    }
//
//    @ApiOperation(value = "search local", notes = "本地检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/local", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response local(@Valid @RequestBody GadYuntuSearchLocalRequestBody ro) {
//        return Response.success(gadYuntuSearchService.searchLocal(ro));
//    }
//
//    @ApiOperation(value = "search around", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/around", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response around(@Valid @RequestBody GadYuntuSearchAroundRequestBody ro) {
//        return Response.success(gadYuntuSearchService.searchAround(ro));
//    }
//
//    @ApiOperation(value = "search id", notes = "ID检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response local(@PathVariable Long id) {
//        return Response.success(gadYuntuSearchService.searchId(id));
//    }
//
//    @ApiOperation(value = "search list", notes = "条件检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/list", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@Valid @RequestBody GadYuntuSearchListRequestBody ro) {
//        return Response.success(gadYuntuSearchService.searchList(ro));
//    }
//
//    @ApiOperation(value = "省数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/province", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response province(@Valid @RequestBody GadYuntuSearchProvinceRequestBody ro) {
//        return Response.success(gadYuntuSearchService.searchProvince(ro));
//    }
//
//    @ApiOperation(value = "市数据分布检索请求", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/city", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response city(@Valid @RequestBody GadYuntuSearchCityRequestBody ro) {
//        return Response.success(yuntuService.searchCity(ro));
//    }
//
//    @ApiOperation(value = "区县数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/district", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response district(@Valid @RequestBody GadYuntuSearchDistrictRequestBody ro) {
//        return Response.success(gadYuntuSearchService.searchDistrict(ro));
//    }

}
