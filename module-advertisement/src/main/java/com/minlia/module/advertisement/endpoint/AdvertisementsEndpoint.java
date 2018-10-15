package com.minlia.module.advertisement.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.advertisement.bean.qo.AdvertisementsQO;
import com.minlia.module.advertisement.bean.to.AdvertisementsCTO;
import com.minlia.module.advertisement.bean.to.AdvertisementsUTO;
import com.minlia.module.advertisement.constant.AdvertisementConstants;
import com.minlia.module.advertisement.service.AdvertisementsService;
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
public class AdvertisementsEndpoint {

	@Autowired
	private AdvertisementsService advertisementsService;

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody AdvertisementsCTO cto) {
		return Response.success(advertisementsService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody AdvertisementsUTO uto) {
		return Response.success(advertisementsService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		advertisementsService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(advertisementsService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody AdvertisementsQO qo) {
		return Response.success(advertisementsService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody AdvertisementsQO qo) {
		return Response.success(advertisementsService.one(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdvertisementConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody AdvertisementsQO qo) {
		return Response.success(advertisementsService.list(qo));
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody AdvertisementsQO qo) {
		return Response.success(advertisementsService.page(qo, pageable));
	}

}