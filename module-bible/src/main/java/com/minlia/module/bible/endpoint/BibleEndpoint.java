package com.minlia.module.bible.endpoint;

import com.github.pagehelper.Page;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bible.body.BibleCreateRequestBody;
import com.minlia.module.bible.body.BibleQueryRequestBody;
import com.minlia.module.bible.body.BibleUpdateRequestBody;
import com.minlia.module.bible.constant.BibleConstant;
import com.minlia.module.bible.service.BibleService;
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
@RequestMapping(value = ApiPrefix.V1 + "bible")
@Api(tags = "System Bible", description = "数据字典")
@Slf4j
public class BibleEndpoint {

  @Autowired
  private BibleService bibleService;

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_CREATE + "')")
  @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody create(@Valid @RequestBody BibleCreateRequestBody body) {
    return SuccessResponseBody.builder().payload(bibleService.create(body)).build();
  }

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_UPDATE + "')")
  @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody update(@Valid @RequestBody BibleUpdateRequestBody body) {
    return SuccessResponseBody.builder().payload(bibleService.update(body)).build();
  }

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_DELETE + "')")
  @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody delete(@PathVariable Long id) {
    bibleService.delete(id);
    return SuccessResponseBody.builder().build();
  }

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "根据ID查询", notes = "根据ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody queryById(@PathVariable Long id) {
    return SuccessResponseBody.builder().payload(bibleService.queryById(id)).build();
  }

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "根据CODO查询", notes = "根据CODO查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "queryByCode/{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody queryByCode(@PathVariable String code) {
    return SuccessResponseBody.builder().payload(bibleService.queryByCode(code)).build();
  }

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "查询集合", notes = "查询集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "queryList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody queryList(@Valid @RequestBody BibleQueryRequestBody body) {
    return SuccessResponseBody.builder().payload(bibleService.queryList(body)).build();
  }

//  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "查询分页", notes = "查询分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "queryPaginated", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody queryPaginated(@Valid @RequestBody BibleQueryRequestBody body, Page page) {
    return SuccessResponseBody.builder().payload(bibleService.queryPaginated(body,page)).build();
  }

}

