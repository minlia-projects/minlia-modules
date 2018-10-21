package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.domain.ArticleCategory;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.qo.ArticleCategoryQO;
import com.minlia.module.article.service.ArticleService;
import com.minlia.module.article.service.ArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "System Article Open", description = "文章-公开")
@RestController
@RequestMapping(value = ApiPrefix.API + "light")
public class ArticleOpenEndpoint {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCategoryService articleCategoryService;

//	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
//	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response findByNumber(@PathVariable Long id) {
//		return Response.success(articleService.queryById(id));
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
//	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response count(@RequestBody ArticleQO qo) {
//		return Response.success(articleService.count(qo));
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
//	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response one(@RequestBody ArticleQO qo) {
//		return Response.success(articleService.one(qo));
//	}

	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleCategoryQO qo) {
		List<ArticleCategory> articleCategoryList = articleCategoryService.list(qo);
		for (ArticleCategory articleCategory : articleCategoryList) {
			articleCategory.setArticles(articleService.list(ArticleQO.builder().categoryId(articleCategory.getId()).build()));
		}
		return Response.success(articleCategoryList);
	}

//	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
//	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response queryPage(@PageableDefault Pageable pageable, @RequestBody ArticleQO qo) {
//		return Response.success(articleService.page(qo, pageable));
//	}

}