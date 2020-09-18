package com.minlia.module.i18n.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.i18n.resource.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2018/8/20.
 */
@Api(tags = "System I18n Open", description = "系统国际化")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "i18n")
public class I18nOpenController {

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(value = "切换语言")
    @GetMapping(value = "locale")
    public Response locale(@RequestParam LocaleEnum lang) {
        LocaleContextHolder.setDefaultLocale(LocaleUtils.toLocale(lang.name()));
        return Response.success();
    }

    @ApiOperation(value = "获取所有")
    @GetMapping(value = "all")
    public Response all() {
        return Response.success(messageSource.getLocalCache(LocaleContextHolder.getLocale()));
    }

    @ApiOperation(value = "获取所有")
    @GetMapping(value = "{locale}")
    public Response all(@PathVariable String locale) {
        return Response.success(messageSource.getLocalCache(locale));
    }

//    @ApiOperation(value = "测试")
//    @PostMapping(value = "test/{i18nkey:.+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response test(@PathVariable String i18nkey) {
//        return Response.success(Lang.get(i18nkey));
//    }

}
