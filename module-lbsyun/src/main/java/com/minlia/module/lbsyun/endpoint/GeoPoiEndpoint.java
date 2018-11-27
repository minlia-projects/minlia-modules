package com.minlia.module.lbsyun.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.lbsyun.body.request.GeoPoiCreateRequest;
import com.minlia.module.lbsyun.body.request.GeoPoiDeleteRequest;
import com.minlia.module.lbsyun.body.request.GeoPoiDetailRequest;
import com.minlia.module.lbsyun.body.request.GeoPoiUpdateRequest;
import com.minlia.module.lbsyun.service.GeoPoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen On 2017/12/14.
 * @author garen
 */
@Api(tags = "Lbsyun Geo Poi", description = "LBS POI")
@RestController
@RequestMapping(value = ApiPrefix.API + "lbs/geopoi")
public class GeoPoiEndpoint {

    @Autowired
    private GeoPoiService geoPoiService;

    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody GeoPoiCreateRequest request) {
        return Response.success(geoPoiService.create(request,null));
    }

    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody GeoPoiUpdateRequest request) {
        return Response.success(geoPoiService.update(request,null));
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "delete", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@Valid @RequestBody GeoPoiDeleteRequest request) {
        return Response.success(geoPoiService.delete(request,null));
    }

    @ApiOperation(value = "详情", notes = "详情", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response detail(@Valid @RequestBody GeoPoiDetailRequest request) {
        return Response.success(geoPoiService.detail(request));
    }
//
//    @ApiOperation(value = "search id", notes = "ID检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response local(@PathVariable Long id) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchId(id));
//    }
//
//    @ApiOperation(value = "search list", notes = "条件检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/list", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@Valid @RequestBody GadYuntuSearchListRequestBody bean) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchList(bean));
//    }
//
//    @ApiOperation(value = "省数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/province", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response province(@Valid @RequestBody GadYuntuSearchProvinceRequestBody bean) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchProvince(bean));
//    }
//
//    @ApiOperation(value = "市数据分布检索请求", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/city", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response city(@Valid @RequestBody GadYuntuSearchCityRequestBody bean) {
//        return SuccessResponseBody.builder().payload(yuntuService.searchCity(bean));
//    }
//
//    @ApiOperation(value = "区县数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/district", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response district(@Valid @RequestBody GadYuntuSearchDistrictRequestBody bean) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchDistrict(bean));
//    }

}
