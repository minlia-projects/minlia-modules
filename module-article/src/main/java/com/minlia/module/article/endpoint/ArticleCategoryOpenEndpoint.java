package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.service.ArticleCategoryService;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
//
//    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response id(@PathVariable Long id) {
//        ArticleCategory articleCategory = articleCategoryService.selectByPrimaryKey(id);
//        articleCategory.setArticles(articleCategoryService.queryArticleByCategoryId(articleCategory.getId()));
//        return Response.success(articleCategory);
//    }

    @ApiOperation(value = "编码查询", notes = "编码查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response id(@PathVariable String code) {
        ArticleCategory articleCategory = articleCategoryService.one(ArticleCategoryQRO.builder().code(code).locale(LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString())).build());
        if (null != articleCategory) {
            articleCategory.setArticles(articleCategoryService.queryArticleByCategoryId(articleCategory.getId()));
        }
//        return Response.success(bindChildren(articleCategory));
        return Response.success(articleCategory);
    }


    @ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response one(@RequestBody ArticleCategoryQRO qro) {
        qro.setDisFlag(false);
        qro.setDelFlag(false);
        List<ArticleCategory> categories = articleCategoryService.list(qro);
        if (CollectionUtils.isNotEmpty(categories)) {
            categories.get(0).setArticles(articleCategoryService.queryArticleByCategoryId(categories.get(0).getId()));
//            return Response.success(bindChildren(categories.get(0)));
            return Response.success(categories.get(0));
        } else {
            return Response.success();
        }
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody ArticleCategoryQRO qro) {
        qro.setDisFlag(false);
        qro.setDelFlag(false);
        List<ArticleCategory> categories = articleCategoryService.list(qro);
        categories.stream().forEach(articleCategory -> articleCategory.setArticles(articleCategoryService.queryArticleByCategoryId(articleCategory.getId())));
        if (null != qro.getParentId()) {
            return Response.success(bindChildren(categories));
        } else {
            qro.setParentIdIsNull(true);
            return Response.success(categories);
        }
    }

//	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleCategoryQRO qro) {
//		qro.setEnabled(true);
//		return Response.success(articleCategoryService.page(qro, pageable));
//	}

    private List<ArticleCategory> bindChildren(List<ArticleCategory> articleCategories) {
        if (CollectionUtils.isNotEmpty(articleCategories)) {
            articleCategories.stream().forEach(articleCategory -> bindChildren(articleCategory));
        }
        return articleCategories;
    }

    private ArticleCategory bindChildren(ArticleCategory articleCategory) {
        if (null != articleCategory) {
            //获取子项
            List<ArticleCategory> children = articleCategoryService.list(ArticleCategoryQRO.builder().parentId(articleCategory.getId()).delFlag(false).disFlag(false).build());
            if (CollectionUtils.isNotEmpty(children)) {
                articleCategory.setChildren(children);
                children.stream().forEach(category -> bindChildren(category));
            }
        }
        return articleCategory;
    }

}