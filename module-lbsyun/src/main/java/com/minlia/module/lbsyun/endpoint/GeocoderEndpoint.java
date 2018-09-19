package com.minlia.module.lbsyun.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.lbsyun.body.request.GeoCoderRequest;
import com.minlia.module.lbsyun.service.GeocoderService;
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
@Api(tags = "Lbsyun Geo Coder", description = "百度地理编码")
@RestController
@RequestMapping(value = ApiPrefix.API + "geocoder")
public class GeocoderEndpoint {

    @Autowired
    private GeocoderService geocoderService;

    @ApiOperation(value = "逆地理编码", notes = "逆地理编码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "regeo", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response regeo(@Valid @RequestBody GeoCoderRequest request) {
        return Response.success(geocoderService.regeo(request));
    }

}
