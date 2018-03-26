package com.minlia.module.richtext.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.richtext.body.RichtextCreateRequestBody;
import com.minlia.module.richtext.body.RichtextQueryRequestBody;
import com.minlia.module.richtext.body.RichtextUpdateRequestBody;
import com.minlia.module.richtext.constant.RichtextSecurityConstant;
import com.minlia.module.richtext.service.RichtextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Rich Text", description = "富文本")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "richtext")
public class RichtextEndpoint {

    @Autowired
    private RichtextService richtextService;

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextSecurityConstant.CREATE+"')")
    @ApiOperation(value = "create", notes = "create", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody RichtextCreateRequestBody body) {
        return SuccessResponseBody.builder().payload(richtextService.create(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextSecurityConstant.UPDATE+"')")
    @ApiOperation(value = "update", notes = "update", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody RichtextUpdateRequestBody body) {
        return SuccessResponseBody.builder().payload(richtextService.update(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextSecurityConstant.DELETE+"')")
    @ApiOperation(value = "delete", notes = "delete", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@PathVariable Long id) {
        richtextService.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "id", notes = "id", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(richtextService.queryById(id)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "list", notes = "list", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@RequestBody RichtextQueryRequestBody body) {
        return SuccessResponseBody.builder().payload(richtextService.queryList(body)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "page", notes = "page", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody RichtextQueryRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(richtextService.queryPage(requestBody,pageable)).build();
    }

}