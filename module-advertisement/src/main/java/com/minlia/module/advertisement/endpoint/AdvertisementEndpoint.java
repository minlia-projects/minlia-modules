package com.minlia.module.advertisement.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.advertisement.bean.qo.AdvertisementQO;
import com.minlia.module.advertisement.bean.to.AdvertisementCTO;
import com.minlia.module.advertisement.bean.to.AdvertisementUTO;
import com.minlia.module.advertisement.constant.AdSecurityConstants;
import com.minlia.module.advertisement.service.AdvertisementService;
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
@RequestMapping(value = ApiPrefix.V1 + "advertisement")
public class AdvertisementEndpoint {

	@Autowired
	private AdvertisementService advertisementService;

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody AdvertisementCTO cto) {
		return Response.success(advertisementService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody AdvertisementUTO uto) {
		return Response.success(advertisementService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@PathVariable Long id) {
		advertisementService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response findByNumber(@PathVariable Long id) {
		return Response.success(advertisementService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody AdvertisementQO qo) {
		return Response.success(advertisementService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody AdvertisementQO qo) {
		return Response.success(advertisementService.one(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response queryList(@RequestBody AdvertisementQO qo) {
		return Response.success(advertisementService.list(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response queryPage(@PageableDefault Pageable pageable, @RequestBody AdvertisementQO qo) {
		return Response.success(advertisementService.page(qo, pageable));
	}

}