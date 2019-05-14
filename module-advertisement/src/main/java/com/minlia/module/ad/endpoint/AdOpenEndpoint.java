package com.minlia.module.ad.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.ad.bean.entity.Advertisements;
import com.minlia.module.ad.bean.ro.AdQRO;
import com.minlia.module.ad.bean.ro.AdsQRO;
import com.minlia.module.ad.service.AdService;
import com.minlia.module.ad.service.AdsService;
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
@RequestMapping(value = ApiPrefix.OPEN + "light")
public class AdOpenEndpoint {

	@Autowired
	private AdService adService;

	@Autowired
	private AdsService adsService;

//	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
//	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response findByNumber(@PathVariable Long id) {
//		return Response.success(adService.queryById(id));
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
//	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response count(@RequestBody AdQRO ro) {
//		return Response.success(adService.count(ro));
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
//	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response one(@RequestBody AdQRO ro) {
//		return Response.success(adService.one(ro));
//	}

	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response queryList(@RequestBody AdsQRO qro) {
		List<Advertisements> advertisementsList = adsService.list(qro);
		for (Advertisements advertisements : advertisementsList) {
			advertisements.setAdvertisements(adService.list(AdQRO.builder().parentId(advertisements.getId()).build()));
		}
		return Response.success(advertisementsList);
	}

//	@PreAuthorize(value = "hasAnyAuthority('" + AdConstants.SEARCH + "')")
//	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response queryPage(@PageableDefault Pageable pageable, @RequestBody AdQRO ro) {
//		return Response.success(adService.page(ro, pageable));
//	}

}