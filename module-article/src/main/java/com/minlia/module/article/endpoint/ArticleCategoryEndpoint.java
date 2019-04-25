package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.ro.ArticleCategoryCRO;
import com.minlia.module.article.ro.ArticleCategoryURO;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.service.ArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Article Category", description = "文章-类目")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/category")
public class ArticleCategoryEndpoint {

	@Autowired
	private ArticleCategoryService articleCategoryService;

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody ArticleCategoryCRO cto) {
		return Response.success(articleCategoryService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody ArticleCategoryURO uto) {
		return Response.success(articleCategoryService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		articleCategoryService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody ArticleCategoryQRO qo) {
		return Response.success(articleCategoryService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody ArticleCategoryQRO qo) {
		return Response.success(articleCategoryService.one(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleCategoryQRO qo) {
		return Response.success(articleCategoryService.list(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleCategoryQRO qo) {
		return Response.success(articleCategoryService.page(qo, pageable));
	}

}