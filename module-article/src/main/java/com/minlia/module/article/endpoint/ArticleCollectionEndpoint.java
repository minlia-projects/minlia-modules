package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.qo.ArticleCollectionQO;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.service.ArticleCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "System Article Collection", description = "文章-收藏")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/collection")
public class ArticleCollectionEndpoint {

	@Autowired
	private ArticleCollectionService articleCollectionService;

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = "收藏", notes = "收藏", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{articleId}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@PathVariable Long articleId) {
		return Response.success(articleCollectionService.collection(articleId));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = " 是否收藏", notes = "是否收藏", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "collected/{articleId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response isCollected(@PathVariable Long articleId) {
		return Response.success(articleCollectionService.isCollected(articleId));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody ArticleCollectionQO qo) {
		return Response.success(articleCollectionService.queryCount(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleCollectionQO qo) {
		return Response.success(articleCollectionService.queryList(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleCollectionQO qo) {
		return Response.success(articleCollectionService.queryPage(qo, pageable));
	}

}