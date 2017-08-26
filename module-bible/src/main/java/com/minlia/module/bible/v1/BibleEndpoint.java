//package com.minlia.module.bible.v1;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.boot.v1.body.impl.SuccessResponseBody;
//import com.minlia.boot.v1.endpoint.AbstractApiEndpoint;
//import com.minlia.boot.v1.service.IService;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.bible.v1.domain.Bible;
//import com.minlia.module.bible.v1.domain.BibleItem;
//import com.minlia.module.bible.v1.service.BibleItemService;
//import com.minlia.module.bible.v1.service.BibleService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.util.Set;
//
///**
// * Created by will on 6/21/17.
// */
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "bibles")
//@Api(tags = "数据字典", description = "数据字典")
//@Slf4j
//public class BibleEndpoint extends AbstractApiEndpoint<Bible> {
//
//    @Autowired
//    BibleService bibleService;
//
//    @Autowired
//    BibleItemService bibleItemService;
//
////    @Autowired
////    BibleRepository bibleRepository;
//
//    /**
//     * 获取子项集
//     * 使用JSR301在线验证
//     * <p>
//     * 使用非RequestBody类型请求时不能出现consumes
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @PreAuthorize(value = "hasAnyAuthority('"+BibleService.ENTITY_READ+"')")
//    @ApiOperation(value = "获取子项集", notes = "获取子项集", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    //,consumes = MediaType.APPLICATION_JSON_VALUE
//    @RequestMapping(value = "items/{bibleCode}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    //consumes = {MediaType.APPLICATION_JSON_VALUE},
//    public StatefulBody findOne(@PathVariable String bibleCode, HttpServletRequest request, HttpServletResponse response) {
//        Set<BibleItem> found = bibleItemService.findByBible_Code(bibleCode);
//        return SuccessResponseBody.builder().payload(found).build();
//    }
//
//
//    /**
//     * 创建子项
//     * 使用JSR301在线验证
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_CREATE + "')")
//    @ApiOperation(value = "创建子项[根据父CODE]", notes = "创建子项", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "item/create/{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody create(@Valid @RequestBody BibleItem body, @PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
//        BibleItem created = bibleItemService.createItem(code, body);
//        return SuccessResponseBody.builder().payload(created).build();
//    }
//
//    /**
//     * PUT /api/v1/bible/item/update 根据当前实体CODE修改
//     * 更新
//     * 使用JSR301在线验证
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_UPDATE + "')")
//    @ApiOperation(value = "更新子项", notes = "更新子项", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "item/update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody update(@Valid @RequestBody BibleItem body, HttpServletRequest request, HttpServletResponse response) {
//        return SuccessResponseBody.builder().payload(bibleItemService.update(body)).build();
//    }
//
//
//    /**
//     * 删除子项
//     * 使用JSR301在线验证
//     * <p>
//     * 使用非RequestBody类型请求时不能出现consumes
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_DELETE + "')")
//    @ApiOperation(value = "删除子项", notes = "删除子项", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "item/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
//    //consumes = {MediaType.APPLICATION_JSON_VALUE},
//    public StatefulBody deleteItem(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
//        bibleItemService.delete(id);
//        return SuccessResponseBody.builder().message("OK").build();
//    }
//
//
//    /**
//     * 创建
//     * 使用JSR301在线验证
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_CREATE + "')")
//    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody create(@Valid @RequestBody Bible body, HttpServletRequest request, HttpServletResponse response) {
//        return super.create(body);
//    }
//
//    /**
//     * PUT /api/v1/bible/update 根据当前实体id修改
//     * 更新
//     * 使用JSR301在线验证
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_UPDATE + "')")
//    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody update(@Valid @RequestBody Bible body, HttpServletRequest request, HttpServletResponse response) {
//        return super.update(body.getId(), body);
//    }
//
//    /**
//     * 删除
//     * 使用JSR301在线验证
//     * <p>
//     * 使用非RequestBody类型请求时不能出现consumes
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_DELETE + "')")
//    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
//    //consumes = {MediaType.APPLICATION_JSON_VALUE},
//    public StatefulBody delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
//        return super.delete(id);
//    }
//
//    /**
//     * 获取一个指定ID的
//     * 使用JSR301在线验证
//     * <p>
//     * 使用非RequestBody类型请求时不能出现consumes
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_READ + "')")
//    @ApiOperation(value = "获取一个指定ID的", notes = "获取一个指定ID的数据字典", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    //,consumes = MediaType.APPLICATION_JSON_VALUE
//    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    //consumes = {MediaType.APPLICATION_JSON_VALUE},
//    public StatefulBody findOne(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
//        return super.findOne(id);
//    }
//
////    /**
////     * 返回所有
////     * <p>
////     * 使用非RequestBody类型请求时不能出现consumes
////     *
////     * @param request
////     * @param response
////     * @return
////     */
////    @PreAuthorize(value = "hasAnyAuthority('" + BibleService.ENTITY_READ + "')")
////    @ApiOperation(value = "返回所有", notes = "返回所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//////    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
////    public StatefulBody findAll(HttpServletRequest request, HttpServletResponse response) {
////        return super.findAll();
////    }
//
//    @Override
//    protected IService<Bible> getService() {
//        return bibleService;
//    }
//}
