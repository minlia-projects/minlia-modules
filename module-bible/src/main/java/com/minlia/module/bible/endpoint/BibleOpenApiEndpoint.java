//package com.minlia.module.bible.endpoint;
//
//import com.minlia.cloud.body.StatefulBody;
//import com.minlia.cloud.body.impl.SuccessResponseBody;
//import com.minlia.cloud.code.ApiCode;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
//import com.minlia.cloud.utils.ApiPreconditions;
//import com.minlia.module.bible.body.BibleItemQueryRequestBody;
//import com.minlia.module.bible.service.BibleItemService;
//import com.minlia.module.bible.vo.BibleItem;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
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
// * 对外提供的服务
// */
//@RestController
//@RequestMapping(value = ApiPrefix.API + "open/bibles")
//@Api(tags = "Open Bible Api", description = "数据字典")
//@Slf4j
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
//  public StatefulBody findByBibleCode(@PathVariable String code) {
//    List<BibleItem> found = bibleItemService.queryByParentCode(code);
//    ApiPreconditions.checkNotNull(found, ApiCode.NOT_FOUND);
//    return SuccessResponseBody.builder().payload(found).build();
//  }
//
//  @RequestMapping(value = "findAll", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//  @ApiOperation(value = "根据条件查询所有", notes = "根据条件查询所有", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  public StatefulBody findAll(@PageableDefault Pageable pageable, @RequestBody ApiQueryRequestBody<BibleItemQueryRequestBody> body) {
//    return SuccessResponseBody.builder().payload(bibleItemService.queryPage(body.getPayload(), pageable)).build();
//  }
//
//}
