package com.minlia.module.address.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.address.bean.domain.AddressDO;
import com.minlia.module.address.bean.qo.AddressQO;
import com.minlia.module.address.bean.to.AddressCTO;
import com.minlia.module.address.bean.to.AddressUTO;
import com.minlia.module.address.constant.AddressConstants;
import com.minlia.module.address.service.AddressService;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by garen on 2017/6/23.
 */
@Api(tags = "Xmj Address", description = "通讯地址")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "address")
public class AddressEndpoint {

    @Autowired
    private AddressService addressService;

    @PreAuthorize(value = "hasAnyAuthority('" + AddressConstants.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody AddressCTO cto) {
        return Response.success(addressService.create(cto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + AddressConstants.UPDATE + "')")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "",method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody AddressUTO uto) {
        return Response.success(addressService.update(uto));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ AddressConstants.UPDATE +"')")
    @ApiOperation(value = "设置默认", notes = "设置默认", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "def/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response def(@PathVariable Long id) {
        return Response.success(addressService.updateDefault(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ AddressConstants.DELETE +"')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        return Response.success(addressService.delete(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ AddressConstants.READ +"')")
    @ApiOperation(value = "我的", notes = "我的", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "me", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response me() {
        List<AddressDO> list = addressService.queryList(AddressQO.builder().guid(SecurityContextHolder.getCurrentGuid()).enabled(true).build());
        return Response.success(list);
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ AddressConstants.SEARCH +"')")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody AddressQO qo) {
        List<AddressDO> list = addressService.queryList(qo);
        return Response.success(list);
    }

}
