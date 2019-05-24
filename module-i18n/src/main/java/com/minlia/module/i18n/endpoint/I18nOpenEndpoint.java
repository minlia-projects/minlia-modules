package com.minlia.module.i18n.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.i18n.ro.I18nQRO;
import com.minlia.module.i18n.service.I18nService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2018/8/20.
 */
@Api(tags = "System I18n Open", description = "系统国际化")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "i18n")
public class I18nOpenEndpoint {

    @Autowired
    private I18nService i18nService;

    @ApiOperation(value = "切换语言")
    @PostMapping(value = "locale")
    public Response change(@RequestParam LocaleEnum lang) {
        return Response.success();
    }

    @ApiOperation(value = "切换语言")
    @GetMapping(value = "locale")
    public Response change1(@RequestParam LocaleEnum lang) {
        return Response.success();
    }

//    @ApiOperation(value = "测试")
//    @PostMapping(value = "test/{i18nkey:.+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response test(@PathVariable String i18nkey) {
//        return Response.success(Lang.get(i18nkey));
//    }

    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryOne(@PathVariable Long id) {
        return Response.success(i18nService.queryOne(id));
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryList(@RequestBody I18nQRO qro) {
        return Response.success(i18nService.queryList(qro));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryPage(@PageableDefault Pageable pageable, @RequestBody I18nQRO qro) {
        return Response.success(i18nService.queryPage(qro, pageable));
    }

}
