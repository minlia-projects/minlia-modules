package com.minlia.module.version.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.version.bean.ro.VersionQRO;
import com.minlia.module.version.enumeration.PlatformTypeEnum;
import com.minlia.module.version.service.VersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "System Version", description = "版本")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "version")
public class VersionOpenEndpoint {

	@Autowired
	private VersionService versionService;

//	@PreAuthorize(value = "hasAnyAuthority('" + VersionConstants.SEARCH + "')")
//	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response id(@PathVariable Long id) {
//		return Response.success(versionService.queryById(id));
//	}

	@ApiOperation(value = "检查更新", notes = "检查更新", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "checkupdate", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestParam("type") PlatformTypeEnum type) {
		return Response.success(versionService.one(VersionQRO.builder().type(type).enabled(true).newest(true).build()));
	}

	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody VersionQRO qo) {
		return Response.success(versionService.one(qo));
	}

}