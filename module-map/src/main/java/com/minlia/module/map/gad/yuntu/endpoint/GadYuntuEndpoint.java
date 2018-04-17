package com.minlia.module.map.gad.yuntu.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.map.gad.yuntu.body.*;
import com.minlia.module.map.gad.yuntu.service.GadYuntuService;
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

    @ApiOperation(value = "create data test", notes = "创建数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "createData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody createData() {
        yuntuService.createData(null);
        return SuccessResponseBody.builder().build();
    }

    @ApiOperation(value = "update data test", notes = "更新数据", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "updateData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody updateData() {
        yuntuService.updateData(null);
        return SuccessResponseBody.builder().build();
    }

    @ApiOperation(value = "search local", notes = "本地检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/local", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody local(@Valid @RequestBody GadYuntuSearchLocalRequestBody body) {
        return SuccessResponseBody.builder().payload(yuntuService.searchLocal(body)).build();
    }

    @ApiOperation(value = "search around", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/around", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody around(@Valid @RequestBody GadYuntuSearchAroundRequestBody body) {
        return SuccessResponseBody.builder().payload(yuntuService.searchAround(body)).build();
    }

    @ApiOperation(value = "search id", notes = "ID检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody local(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(yuntuService.searchId(id)).build();
    }

    @ApiOperation(value = "search list", notes = "条件检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@Valid @RequestBody GadYuntuSearchListRequestBody body) {
        return SuccessResponseBody.builder().payload(yuntuService.searchList(body)).build();
    }

    @ApiOperation(value = "省数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/statistics/province", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody province(@Valid @RequestBody GadYuntuSearchProvinceRequestBody body) {
        return SuccessResponseBody.builder().payload(yuntuService.searchProvince(body)).build();
    }

    @ApiOperation(value = "市数据分布检索请求", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/statistics/city", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody city(@Valid @RequestBody GadYuntuSearchCityRequestBody body) {
        return SuccessResponseBody.builder().payload(yuntuService.searchCity(body)).build();
    }

    @ApiOperation(value = "区县数据分布检索", notes = "分布检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "search/statistics/district", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody district(@Valid @RequestBody GadYuntuSearchDistrictRequestBody body) {
        return SuccessResponseBody.builder().payload(yuntuService.searchDistrict(body)).build();
    }

}
