package com.minlia.module.map.place.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.map.place.body.request.GadPlaceAroundRequestBody;
import com.minlia.module.map.place.service.GadPlaceService;
import com.minlia.module.map.yuntu.body.GadYuntuSearchAroundRequestBody;
import com.minlia.module.map.yuntu.service.GadYuntuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen On 2017/12/14.
 */
@Api(tags = "Gad Place", description = "搜索")
@RestController
@RequestMapping(value = ApiPrefix.API + "gad/place")
public class GadPlaceEndpoint {

    @Autowired
    private GadPlaceService gadPlaceService;

    @ApiOperation(value = "around", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "around", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody around(@Valid @RequestBody GadPlaceAroundRequestBody body) {
        return SuccessResponseBody.builder().payload(gadPlaceService.around(body)).build();
    }

}
