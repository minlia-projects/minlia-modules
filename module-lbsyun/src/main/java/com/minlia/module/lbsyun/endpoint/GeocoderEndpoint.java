package com.minlia.module.lbsyun.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.lbsyun.body.request.*;
import com.minlia.module.lbsyun.service.GeoPoiService;
import com.minlia.module.lbsyun.service.GeocoderService;
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
@Api(tags = "Lbsyun Geo Coder", description = "百度地理编码")
@RestController
@RequestMapping(value = ApiPrefix.API + "geocoder")
public class GeocoderEndpoint {

    @Autowired
    private GeocoderService geocoderService;

    @ApiOperation(value = "逆地理编码", notes = "逆地理编码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "regeo", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody regeo(@Valid @RequestBody GeoCoderRequest request) {
        return SuccessResponseBody.builder().payload(geocoderService.regeo(request)).build();
    }

}
