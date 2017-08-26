package com.minlia.module.bible.v1.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.v1.body.BibleQueryRequestBody;
import com.minlia.module.bible.v1.domain.BibleItem;
import com.minlia.module.bible.v1.service.BibleItemReadOnlyService;
import com.minlia.module.bible.v1.service.BibleReadOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/28/17.
 * 对外提供的服务
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "open/bibles")
@Api(tags = "数据字典", description = "数据字典")
@Slf4j
public class BibleOpenApiEndpoint {

  @Autowired
  BibleReadOnlyService bibleReadOnlyService;
  @Autowired
  BibleItemReadOnlyService bibleItemService;

  /**
   * 根据CODE取出Bible
   */
  @ApiOperation(value = "[公开接口]根据父CODE取到字典值", notes = "[公开接口]根据父CODE取到字典值", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{code}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody get(@PathVariable String code) {
    Set<BibleItem> found = bibleItemService.findByBible_Code(code);
    ApiPreconditions.checkNotNull(found, ApiCode.NOT_FOUND);
    bibleReadOnlyService.findAll();
    return SuccessResponseBody.builder().payload(found).build();
  }


  //  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @RequestMapping(value = "conditions", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "根据条件查询所有", notes = "根据条件查询所有", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
  public StatefulBody searchByConditions(@PageableDefault Pageable pageable,
      @RequestBody ApiQueryRequestBody<BibleQueryRequestBody> body) {
    return SuccessResponseBody.builder()
        .payload(bibleReadOnlyService.findAll(body, pageable)).build();
  }

//    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
//    @RequestMapping(value = "{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ApiOperation(value = "查询所有子项[根据父code]", notes = "查询所有子项[根据父code]", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody searchByConditions(@PathVariable String code, @PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<BibleQueryRequestBody> body) {
//        body.getConditions().add(new QueryCondition("bible.code", QueryOperator.eq, code));
//        return SuccessResponseBody.builder().payload(repository.findAll(spec.buildSpecification(body), pageable)).build();
//    }


}
