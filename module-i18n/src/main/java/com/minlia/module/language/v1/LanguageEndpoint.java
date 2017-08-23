package com.minlia.module.language.v1;

import com.minlia.cloud.body.ApiResponseBody;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.language.v1.domain.Language;
import com.minlia.module.language.v1.service.LanguageReadOnlyService;
import com.minlia.module.language.v1.service.LanguageWriteOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by will on 6/17/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1+"language")
@Api(tags = "System Language", description = "系统国际化")
@Slf4j
public class LanguageEndpoint {

//    @Autowired
//    LanguageService languageService;


    @Autowired
    LanguageReadOnlyService languageReadOnlyService;



    @Autowired
    LanguageWriteOnlyService languageWriteOnlyService;




//    @Autowired
//    LanguageQueryDao languageQueryDao;

    /**
     * 创建语言项
     * 使用JSR301在线验证
     *
     * @param request
     * @param response
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+LanguageService.ENTITY_CREATE+"')")
    @ApiOperation(value = "创建语言项", notes = "创建语言项", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody Language body, HttpServletRequest request, HttpServletResponse response) {
        Language created = languageWriteOnlyService.save(body);
        return SuccessResponseBody.builder().payload(created).build();
    }

    /**
     * 更新语言项
     * 使用JSR301在线验证
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ LanguageService.ENTITY_UPDATE +"')")
    @ApiOperation(value = "更新语言项", notes = "更新语言项", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponseBody update(@Valid @RequestBody Language body, HttpServletRequest request, HttpServletResponse response) {
        return SuccessResponseBody.builder().payload(languageWriteOnlyService.update(body)).build();
    }

    /**
     * 删除语言项
     * 使用JSR301在线验证
     * <p>
     * 使用非RequestBody类型请求时不能出现consumes
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ LanguageService.ENTITY_DELETE +"')")
    @ApiOperation(value = "删除语言项", notes = "删除语言项", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    //consumes = {MediaType.APPLICATION_JSON_VALUE},
    public ApiResponseBody delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        languageWriteOnlyService.delete(id);
        return SuccessResponseBody.builder().message("OK").build();
    }

    /**
     * 获取一个指定ID的
     * 使用JSR301在线验证
     * <p>
     * 使用非RequestBody类型请求时不能出现consumes
     *
     * @param request
     * @param response
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+LanguageService.ENTITY_READ+"')")
    @ApiOperation(value = "获取一个指定ID的", notes = "获取一个指定ID的语言项", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    //,consumes = MediaType.APPLICATION_JSON_VALUE
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    //consumes = {MediaType.APPLICATION_JSON_VALUE},
    public ApiResponseBody findOne(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Language found = languageReadOnlyService.findOne(id);
        return SuccessResponseBody.builder().payload(found).build();
    }

    /**
     * 查找所有
     * 使用JSR301在线验证
     * <p>
     * 使用非RequestBody类型请求时不能出现consumes
     *
     * @param request
     * @param response
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+LanguageService.ENTITY_SEARCH+"')")
    @ApiOperation(value = "查找所有", notes = "查找所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    //,consumes = MediaType.APPLICATION_JSON_VALUE
    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    //consumes = {MediaType.APPLICATION_JSON_VALUE},
    public ApiResponseBody findAll(@PageableDefault Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
        Page<Language> found = languageReadOnlyService.findAll(pageable);
        return SuccessResponseBody.builder().payload(found).build();
    }

}
