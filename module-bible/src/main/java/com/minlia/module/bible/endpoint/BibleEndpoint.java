package com.minlia.module.bible.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bible.bean.qo.BibleQO;
import com.minlia.module.bible.bean.to.BibleCTO;
import com.minlia.module.bible.bean.to.BibleUTO;
import com.minlia.module.bible.constant.BibleConstants;
import com.minlia.module.bible.service.BibleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bible")
@Api(tags = "System Bible", description = "数据字典")
@Slf4j
public class BibleEndpoint {

    @Autowired
    private BibleService bibleService;

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody BibleCTO cto) {
        return Response.success(bibleService.create(cto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody BibleUTO uto) {
        return Response.success(bibleService.update(uto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        bibleService.delete(id);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryById(@PathVariable Long id) {
        return Response.success(bibleService.queryById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "根据CODE查询", notes = "根据CODE查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryByCode", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByCode(@RequestParam String code) {
        return Response.success(bibleService.queryByCode(code));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "根据BODY查询集合", notes = "查询集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryList(@Valid @RequestBody BibleQO qo) {
        return Response.success(bibleService.queryList(qo));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "根据BODY查询分页", notes = "查询分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryPage", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPaginated(@PageableDefault Pageable pageable, @RequestBody BibleQO qo) {
        return Response.success(bibleService.queryPage(qo, pageable));
    }

}

