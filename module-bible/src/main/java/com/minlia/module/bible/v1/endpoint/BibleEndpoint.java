package com.minlia.module.bible.v1.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bible.v1.constant.BibleConstant;
import com.minlia.module.bible.v1.domain.Bible;
import com.minlia.module.bible.v1.domain.BibleItem;
import com.minlia.module.bible.v1.service.BibleItemReadOnlyService;
import com.minlia.module.bible.v1.service.BibleItemWriteOnlyService;
import com.minlia.module.bible.v1.service.BibleReadOnlyService;
import com.minlia.module.bible.v1.service.BibleWriteOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bibles")
@Api(tags = "System Bible", description = "数据字典")
@Slf4j
public class BibleEndpoint {

  @Autowired
  BibleWriteOnlyService bibleWriteOnlyService;
  @Autowired
  BibleReadOnlyService bibleReadOnlyService;

  @Autowired
  BibleItemReadOnlyService bibleItemReadOnlyService;
  @Autowired
  BibleItemWriteOnlyService bibleItemWriteOnlyService;

//    @Autowired
//    BibleRepository bibleRepository;

  /**
   * 获取子项集
   * 使用JSR301在线验证
   * <p>
   * 使用非RequestBody类型请求时不能出现consumes
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "获取子项集", notes = "获取子项集", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  //,consumes = MediaType.APPLICATION_JSON_VALUE
  @RequestMapping(value = "items/{bibleCode}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  //consumes = {MediaType.APPLICATION_JSON_VALUE},
  public StatefulBody findOne(@PathVariable String bibleCode) {
    Set<BibleItem> found = bibleItemReadOnlyService.findByBible_Code(bibleCode);
    return SuccessResponseBody.builder().payload(found).build();
  }


  /**
   * 创建子项
   * 使用JSR301在线验证
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_CREATE + "')")
  @ApiOperation(value = "创建子项[根据父CODE]", notes = "创建子项", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "item/create/{code}", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody create(@Valid @RequestBody BibleItem body, @PathVariable String code) {
    BibleItem created = bibleItemWriteOnlyService.createItem(code, body);
    return SuccessResponseBody.builder().payload(created).build();
  }

  /**
   * PUT /api/v1/bible/item/update 根据当前实体CODE修改
   * 更新
   * 使用JSR301在线验证
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_UPDATE + "')")
  @ApiOperation(value = "更新子项", notes = "更新子项", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "item/update", method = RequestMethod.PUT, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody update(@Valid @RequestBody BibleItem body) {
    return SuccessResponseBody.builder().payload(bibleItemWriteOnlyService.update(body)).build();
  }


  /**
   * 删除子项
   * 使用JSR301在线验证
   * <p>
   * 使用非RequestBody类型请求时不能出现consumes
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_DELETE + "')")
  @ApiOperation(value = "删除子项", notes = "删除子项", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "item/{id}", method = RequestMethod.DELETE, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  //consumes = {MediaType.APPLICATION_JSON_VALUE},
  public StatefulBody deleteItem(@PathVariable Long id) {
    bibleItemWriteOnlyService.delete(id);
    return SuccessResponseBody.builder().message("OK").build();
  }


  /**
   * 创建
   * 使用JSR301在线验证
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_CREATE + "')")
  @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody create(@Valid @RequestBody Bible body) {
    Bible created = bibleWriteOnlyService.create(body);
    return SuccessResponseBody.builder().payload(created).build();
  }

  /**
   * PUT /api/v1/bible/update 根据当前实体id修改
   * 更新
   * 使用JSR301在线验证
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_UPDATE + "')")
  @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody update(@Valid @RequestBody Bible body) {
    Bible updated = bibleWriteOnlyService.update(body);
    return SuccessResponseBody.builder().payload(updated).build();
  }

  /**
   * 删除
   * 使用JSR301在线验证
   * <p>
   * 使用非RequestBody类型请求时不能出现consumes
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_DELETE + "')")
  @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody delete(@PathVariable Long id) {
    bibleWriteOnlyService.delete(id);
    return SuccessResponseBody.builder().message("OK").build();
  }

  /**
   * 获取一个指定ID的
   * 使用JSR301在线验证
   * <p>
   * 使用非RequestBody类型请求时不能出现consumes
   */
  @PreAuthorize(value = "hasAnyAuthority('" + BibleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "获取一个指定ID的数据字典", notes = "获取一个指定ID的数据字典", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  //,consumes = MediaType.APPLICATION_JSON_VALUE
  @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  //consumes = {MediaType.APPLICATION_JSON_VALUE},
  public StatefulBody findOne(@PathVariable Long id) {
    Bible found = bibleReadOnlyService.findOne(id);
    return SuccessResponseBody.builder().payload(found).build();
  }

}

