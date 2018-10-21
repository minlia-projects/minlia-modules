package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.qo.ArticlePraiseQO;
import com.minlia.module.article.bean.to.ArticlePraiseTO;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.service.ArticlePraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Article Praise", description = "文章-点赞")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/praise")
public class ArticlePraiseEndpoint {

	@Autowired
	private ArticlePraiseService articlePraiseService;

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.CREATE + "')")
	@ApiOperation(value = "点赞", notes = "点赞", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{articleId}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody ArticlePraiseTO to) {
		return articlePraiseService.operate(to);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(articlePraiseService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody ArticlePraiseQO qo) {
		return Response.success(articlePraiseService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody ArticlePraiseQO qo) {
		return Response.success(articlePraiseService.one(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticlePraiseQO qo) {
		return Response.success(articlePraiseService.list(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticlePraiseQO qo) {
		return Response.success(articlePraiseService.page(qo, pageable));
	}

}