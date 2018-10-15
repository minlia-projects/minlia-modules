package com.minlia.module.country.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.country.bean.domain.Country;
import com.minlia.module.country.bean.qo.CountryQO;
import com.minlia.module.country.bean.to.CountryCTO;
import com.minlia.module.country.bean.to.CountryUTO;
import com.minlia.module.country.contract.CountryContracts;
import com.minlia.module.country.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Country", description = "国家")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "country")
public class CountryEndpoint {

    @Autowired
    private CountryService countryService;

    @PreAuthorize(value = "hasAnyAuthority('" + CountryContracts.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody CountryCTO cto) {
        return Response.success(countryService.create(cto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + CountryContracts.UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody CountryUTO uto) {
        return Response.success(countryService.update(uto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + CountryContracts.DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        countryService.delete(id);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + CountryContracts.CREATE + "')")
    @ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response findOne(@PathVariable Long id) {
        Country x = countryService.queryById(id);
        return Response.success(x);
    }

    @PreAuthorize(value = "hasAnyAuthority('" + CountryContracts.CREATE + "')")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody CountryQO qo) {
        return Response.success(countryService.queryList(qo));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + CountryContracts.CREATE + "')")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response paginated(@PageableDefault Pageable pageable, @RequestBody CountryQO qo) {
        return Response.success(countryService.queryPage(qo, pageable));
    }

}