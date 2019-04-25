package com.minlia.module.ad.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.ad.ro.AdQRO;
import com.minlia.module.ad.ro.AdCRO;
import com.minlia.module.ad.ro.AdURO;
import com.minlia.module.ad.constant.AdConstants;
import com.minlia.module.ad.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Advertisement", description = "广告")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "ad")
public class AdEndpoint {

	@Autowired
	private AdService adService;

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody AdCRO cto) {
		return Response.success(adService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody AdURO uto) {
		return Response.success(adService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		adService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(adService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody AdQRO qo) {
		return Response.success(adService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody AdQRO qo) {
		return Response.success(adService.one(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody AdQRO qo) {
		return Response.success(adService.list(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody AdQRO qo) {
		return Response.success(adService.page(qo, pageable));
	}

}