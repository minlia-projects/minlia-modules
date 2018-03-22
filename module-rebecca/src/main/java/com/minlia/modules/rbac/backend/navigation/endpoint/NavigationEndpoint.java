
package com.minlia.modules.rbac.backend.navigation.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.common.constant.RebeccaSecurityConstant;
import com.minlia.modules.rbac.backend.navigation.body.NavigationCreateRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationGrantRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationQueryRequestBody;
import com.minlia.modules.rbac.backend.navigation.body.NavigationUpdateRequestBody;
import com.minlia.modules.rbac.backend.navigation.entity.Navigation;
import com.minlia.modules.rbac.backend.navigation.service.NavigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by will on 6/17/17.
 */
@Api(tags = "System Navigation", description = "菜单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "navigation")
public class NavigationEndpoint {

    @Autowired
    private NavigationService navigationService;

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody createByParent(@Valid @RequestBody NavigationCreateRequestBody body) {
        return navigationService.create(body);
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody NavigationUpdateRequestBody body) {
        return SuccessResponseBody.builder().payload(navigationService.update(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        navigationService.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_GRANT + "')")
    @ApiOperation(value = "授权", notes = "给角色分配菜单", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "grant", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody grant(@Valid @RequestBody NavigationGrantRequestBody body) {
        navigationService.grant(body);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_GRANT + "')")
    @ApiOperation(value = "显示/隐藏", notes = "显示", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "display", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody display(@RequestParam Long id) {
        return SuccessResponseBody.builder().payload(navigationService.display(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@RequestParam Long id) {
        return SuccessResponseBody.builder().payload(navigationService.queryById(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "根据父ID查询", notes = "根据父ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByParentId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryByParentId(@RequestParam Long id) {
        return SuccessResponseBody.builder().payload(navigationService.queryByParentId(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "根据角色ID查询", notes = "根据角色ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByRoleId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryByRoleId(@RequestParam Long id) {
        return SuccessResponseBody.builder().payload(navigationService.queryByRoleId(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "queryList", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryList(@RequestBody NavigationQueryRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(navigationService.queryList(requestBody)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "查询分页", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "queryPage", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryPage(@PageableDefault(direction = Sort.Direction.ASC,sort = "id")Pageable pageable,@RequestBody NavigationQueryRequestBody requestBody) {
        PageHelper.startPage(new Integer(new Long(pageable.getOffset()).toString()), pageable.getPageSize());
        List<Navigation> navigations =  navigationService.queryList(requestBody);
        PageInfo<Navigation> pageInfo = new PageInfo<Navigation>(navigations);

//        navigationService.queryPage(rowBounds);

        return SuccessResponseBody.builder().payload(pageInfo).build();
    }

}
