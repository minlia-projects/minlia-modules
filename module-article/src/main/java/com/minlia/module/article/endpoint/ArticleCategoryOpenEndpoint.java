package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.service.ArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "System Article Category OPEN", description = "文章-类目")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article/category")
public class ArticleCategoryOpenEndpoint {

    @Autowired
    private ArticleCategoryService articleCategoryService;

//	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response count(@RequestBody ArticleCategoryQRO qro) {
//		qro.setEnabled(true);
//		return Response.success(articleCategoryService.count(qro));
//	}
//
//	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response one(@RequestBody ArticleCategoryQRO qro) {
//		qro.setEnabled(true);
//		return Response.success(articleCategoryService.one(qro));
//	}

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody ArticleCategoryQRO qro) {
        qro.setDisFlag(false);
        List<ArticleCategory> categories = articleCategoryService.list(qro);
        categories.stream().forEach(articleCategory -> articleCategory.setArticles(articleCategoryService.queryArticleByCategoryId(articleCategory.getId())));
        return Response.success();
    }

//	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleCategoryQRO qro) {
//		qro.setEnabled(true);
//		return Response.success(articleCategoryService.page(qro, pageable));
//	}

}