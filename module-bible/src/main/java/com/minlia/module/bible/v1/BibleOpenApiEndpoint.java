package com.minlia.module.bible.v1;

import com.minlia.boot.utils.ApiPreconditions;
import com.minlia.boot.v1.body.StatefulBody;
import com.minlia.boot.v1.body.impl.SuccessResponseBody;
import com.minlia.boot.v1.code.ApiCode;
import com.minlia.boot.v1.web.ApiPrefix;
import com.minlia.module.bible.v1.domain.BibleItem;
import com.minlia.module.bible.v1.service.BibleItemService;
import com.minlia.module.bible.v1.service.BibleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by will on 6/28/17.
 * <p>
 * 对外提供的服务
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "open/bibles")
@Api(tags = "数据字典", description = "数据字典")
@Slf4j
public class BibleOpenApiEndpoint {

    @Autowired
    BibleService bibleService;
    @Autowired
    BibleItemService bibleItemService;

    /**
     * 根据CODE取出Bible
     */
    @ApiOperation(value = "[公开接口]根据父CODE取到字典值", notes = "[公开接口]根据父CODE取到字典值", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody get(@PathVariable String code) {
        Set<BibleItem> found = bibleItemService.findByBible_Code(code);
        ApiPreconditions.checkNotNull(found, ApiCode.NOT_FOUND);
        bibleService.findAll();
        return SuccessResponseBody.builder().payload(found).build();
    }

}
