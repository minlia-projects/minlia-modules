package com.minlia.module.ad.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.ad.bean.ro.AdsQRO;
import com.minlia.module.ad.bean.ro.AdsCRO;
import com.minlia.module.ad.bean.ro.AdsURO;
import com.minlia.module.ad.constant.AdConstants;
import com.minlia.module.ad.service.AdsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Advertisements", description = "广告集")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "advertisements")
public class AdsEndpoint {

	@Autowired
	private AdsService adsService;

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody AdsCRO cro) {
		return Response.success(adsService.create(cro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody AdsURO uro) {
		return Response.success(adsService.update(uro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		adsService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(adsService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody AdsQRO qro) {
		return Response.success(adsService.count(qro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody AdsQRO qro) {
		return Response.success(adsService.one(qro));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody AdsQRO qro) {
		return Response.success(adsService.list(qro));
	}

//	@PreAuthorize(value = "isAuthenticated()")
//	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response page(@PageableDefault Pageable pageable, @RequestBody AdsQRO qro) {
//		return Response.success(adsService.page(qro, pageable));
//	}

}