package com.minlia.module.advertisement.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.advertisement.bean.domain.Advertisements;
import com.minlia.module.advertisement.bean.qo.AdvertisementQO;
import com.minlia.module.advertisement.bean.qo.AdvertisementsQO;
import com.minlia.module.advertisement.service.AdvertisementService;
import com.minlia.module.advertisement.service.AdvertisementsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "System Advertisement Open", description = "广告")
@RestController
@RequestMapping(value = ApiPrefix.API + "light")
public class AdvertisementOpenEndpoint {

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AdvertisementsService advertisementsService;

//	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
//	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response findByNumber(@PathVariable Long id) {
//		return Response.success(advertisementService.queryById(id));
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
//	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response count(@RequestBody AdvertisementQO qo) {
//		return Response.success(advertisementService.count(qo));
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
//	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response one(@RequestBody AdvertisementQO qo) {
//		return Response.success(advertisementService.one(qo));
//	}

	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response queryList(@RequestBody AdvertisementsQO qo) {
		List<Advertisements> advertisementsList = advertisementsService.list(qo);
		for (Advertisements advertisements : advertisementsList) {
			advertisements.setAdvertisements(advertisementService.list(AdvertisementQO.builder().parentId(advertisements.getId()).build()));
		}
		return Response.success(advertisementsList);
	}

//	@PreAuthorize(value = "hasAnyAuthority('" + AdSecurityConstants.SEARCH + "')")
//	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response queryPage(@PageableDefault Pageable pageable, @RequestBody AdvertisementQO qo) {
//		return Response.success(advertisementService.page(qo, pageable));
//	}

}