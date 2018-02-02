package com.minlia.module.country.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.country.body.CountryCreateRequestBody;
import com.minlia.module.country.body.CountryQueryRequestBody;
import com.minlia.module.country.body.CountryUpdateRequestBody;
import com.minlia.module.country.entity.Country;
import com.minlia.module.country.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Country", description = "国家")
@RestController
@RequestMapping(value = ApiPrefix.API + "country")
public class CountryEndpoint {

    @Autowired
    private CountryService countryService;

    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody CountryCreateRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(countryService.create(requestBody)).build();
    }

    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody CountryUpdateRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(countryService.update(requestBody)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        countryService.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "find/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@PathVariable Long id) {
        Country x = countryService.queryById(id);
        return SuccessResponseBody.builder().payload(x).build();
    }

    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "findList", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@RequestBody CountryQueryRequestBody body) {
        return SuccessResponseBody.builder().payload(countryService.queryList(body)).build();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "findPaginated", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody CountryQueryRequestBody body) {
        return SuccessResponseBody.builder().payload(countryService.queryPage(body,new RowBounds())).build();
    }

}