package com.minlia.module.wechat.mp.material.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.mp.material.service.MpMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by garen on 2018/6/29.
 */
@Api(tags = "Wechat MP Material", description = "微信公众号素材")
@RestController
@RequestMapping(ApiPrefix.API + "wechat/mp")
public class MpMaterialEndpoint {

    @Autowired
    private MpMaterialService materialService;

    @ApiOperation(value = "获取永久素材", notes = "获取永久素材", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "material/{mediaId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody material(@PathVariable String mediaId) throws WxErrorException {
        return materialService.materialGet(mediaId);
    }

    @ApiOperation(value = "获取永久素材列表", notes = "获取永久素材列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "material/batchget/{type}/{offset}/{count}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody material(@PathVariable String type, @PathVariable int offset, @PathVariable int count) throws WxErrorException {
        return materialService.materialBatchGet(type,offset,count);
    }

}
