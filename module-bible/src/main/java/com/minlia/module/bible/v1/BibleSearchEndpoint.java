//package com.minlia.module.bible.v1;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.boot.v1.body.impl.SuccessResponseBody;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.bible.v1.query.BibleSearchRequestBody;
//import com.minlia.module.bible.v1.repository.BibleRepository;
//import com.minlia.module.bible.v1.service.BibleService;
//import com.minlia.module.data.query.v2.DynamicSpecifications;
//import com.minlia.module.data.query.v2.body.ApiSearchRequestBody;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "bibles/search")
//@Api(tags = "数据字典", description = "数据字典")
//@Slf4j
//public class BibleSearchEndpoint {
//
//    @Autowired
//    DynamicSpecifications spec;
//
//    @Autowired
//    BibleRepository bibleRepository;
//
//
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_SEARCH + "')")
//    @RequestMapping(value = "conditions", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ApiOperation(value = "根据条件查询所有", notes = "根据条件查询所有", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody searchByConditions(@PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<BibleSearchRequestBody> body) {
////        body.getConditions().add(new QueryCondition("items.id", Operator.eq,"123"));
//        return SuccessResponseBody.builder().payload(bibleRepository.findAll(spec.buildSpecification(body), pageable)).build();
//    }
//
//
//}