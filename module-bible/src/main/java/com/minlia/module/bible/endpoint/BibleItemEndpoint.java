package com.minlia.module.bible.endpoint;

import com.github.pagehelper.Page;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bible.body.BibleItemCreateRequestBody;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.body.BibleItemUpdateRequestBody;
import com.minlia.module.bible.constant.BibleSecurityConstant;
import com.minlia.module.bible.service.BibleItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bible/item")
@Api(tags = "System Bible Item", description = "数据字典")
@Slf4j
public class BibleItemEndpoint {

    @Autowired
    private BibleItemService bibleItemService;

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody BibleItemCreateRequestBody body) {
        return SuccessResponseBody.builder().payload(bibleItemService.create(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody BibleItemUpdateRequestBody body) {
        return SuccessResponseBody.builder().payload(bibleItemService.update(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        bibleItemService.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.SEARCH + "')")
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryById(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(bibleItemService.queryById(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.SEARCH + "')")
    @ApiOperation(value = "根据CODE查询", notes = "根据CODE查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "get/{parentCode}/{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody get(@PathVariable String parentCode, @PathVariable String code) {
        return SuccessResponseBody.builder().payload(bibleItemService.get(parentCode, code)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.SEARCH + "')")
    @ApiOperation(value = "根据父CODE+CODE查询", notes = "根据父CODE、CODE查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryByParentCodeAndCode/{parentCode}/{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryByParentCodeAndCode(String parentCode, String code) {
        return SuccessResponseBody.builder().payload(bibleItemService.queryByParentCodeAndCode(parentCode, code)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.SEARCH + "')")
    @ApiOperation(value = "根据父CODE查询", notes = "根据父CODE查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryByParentCode/{parentCode}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryByParentCode(@PathVariable String parentCode) {
        return SuccessResponseBody.builder().payload(bibleItemService.queryByParentCode(parentCode)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.SEARCH + "')")
    @ApiOperation(value = "根据BODY查询集合", notes = "查询集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryList(@Valid @RequestBody BibleItemQueryRequestBody body) {
        return SuccessResponseBody.builder().payload(bibleItemService.queryList(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleSecurityConstant.SEARCH + "')")
    @ApiOperation(value = "根据BODY查询分页", notes = "查询分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryPaginated", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryPaginated(@Valid @RequestBody BibleItemQueryRequestBody body, Page page) {
        return SuccessResponseBody.builder().payload(bibleItemService.queryPaginated(body, page)).build();
    }

}

