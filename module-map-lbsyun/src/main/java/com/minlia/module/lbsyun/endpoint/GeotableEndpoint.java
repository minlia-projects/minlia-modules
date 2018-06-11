package com.minlia.module.lbsyun.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
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
    public StatefulBody createTable(@Valid @RequestBody GeotableCreateRequest request) {
        return SuccessResponseBody.builder().payload(geotableService.create(request)).build();
    }

    @ApiOperation(value = "创建列", notes = "创建列", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "cloumn/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody createColumn(@Valid @RequestBody GeocolumnCreateRequest request) {
        return SuccessResponseBody.builder().payload(geotableService.createColumn(request)).build();
    }

//    @ApiOperation(value = "update data test", notes = "更新数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "updateData", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody updateData() {
//        yuntuService.updateData(null);
//        return SuccessResponseBody.builder().build();
//    }
//
//    @ApiOperation(value = "search local", notes = "本地检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/local", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody local(@Valid @RequestBody GadYuntuSearchLocalRequestBody body) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchLocal(body)).build();
//    }
//
//    @ApiOperation(value = "search around", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/around", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody around(@Valid @RequestBody GadYuntuSearchAroundRequestBody body) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchAround(body)).build();
//    }
//
//    @ApiOperation(value = "search id", notes = "ID检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody local(@PathVariable Long id) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchId(id)).build();
//    }
//
//    @ApiOperation(value = "search list", notes = "条件检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/list", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody list(@Valid @RequestBody GadYuntuSearchListRequestBody body) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchList(body)).build();
//    }
//
//    @ApiOperation(value = "省数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/province", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody province(@Valid @RequestBody GadYuntuSearchProvinceRequestBody body) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchProvince(body)).build();
//    }
//
//    @ApiOperation(value = "市数据分布检索请求", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/city", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody city(@Valid @RequestBody GadYuntuSearchCityRequestBody body) {
//        return SuccessResponseBody.builder().payload(yuntuService.searchCity(body)).build();
//    }
//
//    @ApiOperation(value = "区县数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "search/statistics/district", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody district(@Valid @RequestBody GadYuntuSearchDistrictRequestBody body) {
//        return SuccessResponseBody.builder().payload(gadYuntuSearchService.searchDistrict(body)).build();
//    }

}
