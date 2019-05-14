package com.minlia.module.version.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.version.bean.ro.VersionQRO;
import com.minlia.module.version.bean.ro.VersionCRO;
import com.minlia.module.version.bean.ro.VersionURO;
import com.minlia.module.version.constant.VersionConstants;
import com.minlia.module.version.service.VersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Version", description = "版本")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "version")
public class VersionEndpoint {

	@Autowired
	private VersionService versionService;

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody VersionCRO cto) {
		return Response.success(SystemCode.Message.CREATE_SUCCESS, versionService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody VersionURO uto) {
		return Response.success(SystemCode.Message.UPDATE_SUCCESS, versionService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		versionService.delete(id);
		return Response.success(SystemCode.Message.DELETE_SUCCESS);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(versionService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody VersionQRO qro) {
		return Response.success(versionService.count(qro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody VersionQRO qro) {
		return Response.success(versionService.one(qro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody VersionQRO qro) {
		return Response.success(versionService.list(qro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody VersionQRO qro) {
		return Response.success(versionService.page(qro, pageable));
	}

}