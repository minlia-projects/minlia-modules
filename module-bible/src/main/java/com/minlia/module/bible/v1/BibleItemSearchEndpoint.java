package com.minlia.module.bible.v1;

import com.minlia.boot.v1.body.StatefulBody;
import com.minlia.boot.v1.body.impl.SuccessResponseBody;
import com.minlia.boot.v1.web.ApiPrefix;
import com.minlia.module.bible.v1.query.BibleItemSearchRequestBody;
import com.minlia.module.bible.v1.repository.BibleItemRepository;
import com.minlia.module.bible.v1.service.BibleService;
import com.minlia.module.data.query.v2.DynamicSpecifications;
import com.minlia.module.data.query.v2.Operator;
import com.minlia.module.data.query.v2.QueryCondition;
import com.minlia.module.data.query.v2.body.ApiSearchRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bibles/item/search")
@Api(tags = "数据字典", description = "数据字典")
@Slf4j
public class BibleItemSearchEndpoint {


    @Autowired
    DynamicSpecifications spec;

    @Autowired
    BibleItemRepository repository;

    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_SEARCH + "')")
    @RequestMapping(value = "{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "查询所有子项[根据父code]", notes = "查询所有子项[根据父code]", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody searchByConditions(@PathVariable String code, @PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<BibleItemSearchRequestBody> body) {
        body.getConditions().add(new QueryCondition("bible.code", Operator.eq, code));
        return SuccessResponseBody.builder().payload(repository.findAll(spec.buildSpecification(body), pageable)).build();
    }


}