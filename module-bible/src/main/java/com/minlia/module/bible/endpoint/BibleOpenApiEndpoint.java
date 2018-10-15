//package com.minlia.module.bible.endpoint;
//
//import com.minlia.cloud.body.Response;
//import com.minlia.cloud.code.SystemCode;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.cloud.utils.ApiAssert;
//import com.minlia.module.bible.bean.domain.BibleItem;
//import com.minlia.module.bible.bean.qo.BibleItemQO;
//import com.minlia.module.bible.service.BibleItemService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * Created by will on 6/28/17.
// */
//@RestController
//@RequestMapping(value = ApiPrefix.API + "open/bibles")
//@Api(tags = "Open Bible Api", description = "数据字典")
//public class BibleOpenApiEndpoint {
//
//  @Autowired
//  private BibleItemService bibleItemService;
//
//  /**
//   * 根据CODE取出Bible
//   */
//  @ApiOperation(value = "[公开接口]根据父CODE取到字典值", notes = "[公开接口]根据父CODE取到字典值", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//  @RequestMapping(value = "{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//  public Response findByBibleCode(@PathVariable String code) {
//    List<BibleItem> found = bibleItemService.queryList(BibleItemQO.builder().code(code).build());
//    ApiAssert.notNull(found, SystemCode.Message.DATA_NOT_EXISTS);
//    return Response.success(found);
//  }
//
//  @RequestMapping(value = "findAll", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//  @ApiOperation(value = "根据条件查询所有", notes = "根据条件查询所有", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  public Response findAll(@PageableDefault Pageable pageable, @RequestBody BibleItemQO qo) {
//    return Response.success(bibleItemService.queryPage(qo, pageable));
//  }
//
//}
