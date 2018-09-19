package com.minlia.module.gad.yuntu.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.gad.yuntu.body.*;
import com.minlia.module.gad.yuntu.service.GadYuntuSearchService;
import com.minlia.module.gad.yuntu.service.GadYuntuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen On 2017/12/14.
 * @author garen
 */
@RestController
@RequestMapping(value = ApiPrefix.API + "gad/yuntu")
@Api(tags = "Gad yuntu", description = "云图")
@Slf4j
public class GadYuntuEndpoint {

    @Autowired
    private GadYuntuService yuntuService;

    @Autowired
    private GadYuntuSearchService gadYuntuSearchService;

    @ApiOperation(value = "create data test", notes = "创建数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "createData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response createData() {
        yuntuService.createData(null);
        return Response.success();
    }

    @ApiOperation(value = "update data test", notes = "更新数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "updateData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response updateData() {
        yuntuService.updateData(null);
        return Response.success();
    }

    @ApiOperation(value = "search local", notes = "本地检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/local", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response local(@Valid @RequestBody GadYuntuSearchLocalRequestBody body) {
        return Response.success(gadYuntuSearchService.searchLocal(body));
    }

    @ApiOperation(value = "search around", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/around", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response around(@Valid @RequestBody GadYuntuSearchAroundRequestBody body) {
        return Response.success(gadYuntuSearchService.searchAround(body));
    }

    @ApiOperation(value = "search id", notes = "ID检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response local(@PathVariable Long id) {
        return Response.success(gadYuntuSearchService.searchId(id));
    }

    @ApiOperation(value = "search list", notes = "条件检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@Valid @RequestBody GadYuntuSearchListRequestBody body) {
        return Response.success(gadYuntuSearchService.searchList(body));
    }

    @ApiOperation(value = "省数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/statistics/province", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response province(@Valid @RequestBody GadYuntuSearchProvinceRequestBody body) {
        return Response.success(gadYuntuSearchService.searchProvince(body));
    }

    @ApiOperation(value = "市数据分布检索请求", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/statistics/city", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response city(@Valid @RequestBody GadYuntuSearchCityRequestBody body) {
        return Response.success(yuntuService.searchCity(body));
    }

    @ApiOperation(value = "区县数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/statistics/district", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response district(@Valid @RequestBody GadYuntuSearchDistrictRequestBody body) {
        return Response.success(gadYuntuSearchService.searchDistrict(body));
    }

}
