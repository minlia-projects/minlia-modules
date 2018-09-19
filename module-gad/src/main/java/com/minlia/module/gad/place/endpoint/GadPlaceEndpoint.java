package com.minlia.module.gad.place.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.gad.place.body.request.GadPlaceAroundRequestBody;
import com.minlia.module.gad.place.service.GadPlaceService;
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
 */
@Api(tags = "Gad Place", description = "搜索")
@RestController
@RequestMapping(value = ApiPrefix.API + "gad/place")
public class GadPlaceEndpoint {

    @Autowired
    private GadPlaceService gadPlaceService;

    @ApiOperation(value = "around", notes = "周边检索", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "around", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response around(@Valid @RequestBody GadPlaceAroundRequestBody body) {
        return Response.success(gadPlaceService.around(body));
    }

}
