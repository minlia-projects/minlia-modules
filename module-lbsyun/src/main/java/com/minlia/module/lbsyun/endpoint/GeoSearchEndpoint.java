package com.minlia.module.lbsyun.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.lbsyun.body.request.GeoSearchBoundRequest;
import com.minlia.module.lbsyun.body.request.GeoSearchDetailRequest;
import com.minlia.module.lbsyun.body.request.GeoSearchLocalRequest;
import com.minlia.module.lbsyun.body.request.GeoSearchNearbyRequest;
import com.minlia.module.lbsyun.service.GeoSearchService;
import io.swagger.annotationsmi.Api;
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
@Api(tags = "Lbsyun Geo Search", description = "百度云检索")
@RestController
@RequestMapping(value = ApiPrefix.API + "lbs/geosearch")
public class GeoSearchEndpoint {

    @Autowired
    private GeoSearchService geoSearchService;

    @ApiOperation(value = "nearby", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "nearby", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response nearby(@Valid @RequestBody GeoSearchNearbyRequest request) {
        return Response.success(geoSearchService.nearby(request));
    }

    @ApiOperation(value = "bound", notes = "矩形检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "bound", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response bound(@Valid @RequestBody GeoSearchBoundRequest request) {
        return Response.success(geoSearchService.bound(request));
    }

    @ApiOperation(value = "local", notes = "本地检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "local", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response local(@Valid @RequestBody GeoSearchLocalRequest body) {
        return Response.success(geoSearchService.local(body));
    }

    @ApiOperation(value = "detail", notes = "详情检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response detail(@Valid @RequestBody GeoSearchDetailRequest request) {
        return Response.success(geoSearchService.detail(request));
    }

}
