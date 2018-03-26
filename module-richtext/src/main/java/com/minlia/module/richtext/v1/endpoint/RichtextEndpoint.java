package com.minlia.module.richtext.v1.endpoint;

import com.minlia.boot.v1.body.StatefulBody;
import com.minlia.boot.v1.body.impl.SuccessResponseBody;
import com.minlia.boot.v1.web.ApiPrefix;
import com.minlia.module.richtext.v1.body.RichtextCreateBody;
import com.minlia.module.richtext.v1.body.RichtextQueryBody;
import com.minlia.module.richtext.v1.body.RichtextUpdateBody;
import com.minlia.module.richtext.v1.service.RichtextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiPrefix.V1 + "richtext")
@Api(tags = "rich text", description = "rich text")
@Slf4j
public class RichtextEndpoint {

    @Autowired
    private RichtextService richtextService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "create", notes = "create", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody RichtextCreateBody body) {
        return SuccessResponseBody.builder().payload(richtextService.create(body)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('"+ BillSecurityConstants.OPERATION_BILL_UPDATE+"')")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "update", notes = "update", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody RichtextUpdateBody body) {
        return SuccessResponseBody.builder().payload(richtextService.update(body)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
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
        return SuccessResponseBody.builder().payload(richtextService.findOne(RichtextQueryBody.builder().id(id).build())).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "list", notes = "list", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@RequestBody RichtextQueryBody body) {
        return SuccessResponseBody.builder().payload(richtextService.findList(body)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "paginated", notes = "paginated", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "paginated", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody RichtextQueryBody body) {
        return SuccessResponseBody.builder().payload(richtextService.findPage(body,pageable)).build();
    }

}