package com.minlia.module.i18n.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.i18n.bean.I18nCTO;
import com.minlia.module.i18n.bean.I18nDO;
import com.minlia.module.i18n.bean.I18nQO;
import com.minlia.module.i18n.bean.I18nUTO;
import com.minlia.module.i18n.service.I18nService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2018/8/20.
 */
@Api(tags = "System I18n", description = "系统国际化")
@RestController
@RequestMapping(value = ApiPrefix.API+"i18n")
public class I18nEndpoint {

    @Autowired
    private I18nService i18nService;

//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody create(@Valid @RequestBody I18nCTO cto) {
        return SuccessResponseBody.builder().payload(i18nService.create(cto)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_UPDATE + "')")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody update(@Valid @RequestBody I18nUTO uto) {
        return SuccessResponseBody.builder().payload(i18nService.update(uto)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody delete(@PathVariable Long id) {
        i18nService.delete(id);
        return SuccessResponseBody.builder().build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody queryOne(@PathVariable Long id) {
        Lang.get("system.i18n.message.0000011");

//        ContextHolder.getContext().get

        return SuccessResponseBody.builder().payload(i18nService.queryOne(id)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "ID查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody queryList(@RequestBody I18nQO qo) {
        return SuccessResponseBody.builder().payload(i18nService.queryList(qo)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "ID查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody queryPage(@PageableDefault Pageable pageable, @RequestBody I18nQO qo) {
        return SuccessResponseBody.builder().payload(i18nService.queryPage(qo,pageable)).build();
    }

}
