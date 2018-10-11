package com.minlia.module.i18n.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.i18n.bean.I18nCTO;
import com.minlia.module.i18n.bean.I18nQO;
import com.minlia.module.i18n.bean.I18nUTO;
import com.minlia.module.i18n.constant.I18nConstants;
import com.minlia.module.i18n.service.I18nService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2018/8/20.
 */
@Api(tags = "System I18n", description = "系统国际化")
@RestController
@RequestMapping(value = ApiPrefix.V1+"i18n")
public class I18nEndpoint {

    @Autowired
    private I18nService i18nService;

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@Valid @RequestBody I18nCTO cto) {
        return Response.success(i18nService.create(cto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_UPDATE + "')")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@Valid @RequestBody I18nUTO uto) {
        return Response.success(i18nService.update(uto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@PathVariable Long id) {
        i18nService.delete(id);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "测试", notes = "测试", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "test/{i18nkey:.+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response test(@PathVariable String i18nkey) {
        return Response.success(Lang.get(i18nkey));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryOne(@PathVariable Long id) {
        return Response.success(i18nService.queryOne(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "ID查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryList(@RequestBody I18nQO qo) {
        return Response.success(i18nService.queryList(qo));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "ID查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response queryPage(@PageableDefault Pageable pageable, @RequestBody I18nQO qo) {
        return Response.success(i18nService.queryPage(qo,pageable));
    }

}
