package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.ro.ArticleLabelCRO;
import com.minlia.module.article.ro.ArticleLabelQRO;
import com.minlia.module.article.ro.ArticleLabelURO;
import com.minlia.module.article.service.ArticleLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Article Label Open", description = "文章-标签")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article/label")
public class ArticleLabelOpenEndpoint {

	@Autowired
	private ArticleLabelService articleLabelService;

	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(articleLabelService.queryById(id));
	}

	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody ArticleLabelQRO qo) {
		qo.setDisFlag(false);
		qo.setLocale(LocaleContextHolder.getLocale().toString());
		return Response.success(articleLabelService.count(qo));
	}

	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody ArticleLabelQRO qo) {
		qo.setDisFlag(false);
		qo.setLocale(LocaleContextHolder.getLocale().toString());
		return Response.success(articleLabelService.one(qo));
	}

	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleLabelQRO qo) {
		qo.setDisFlag(false);
		qo.setLocale(LocaleContextHolder.getLocale().toString());
		return Response.success(articleLabelService.list(qo));
	}

	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleLabelQRO qo) {
		qo.setDisFlag(false);
		qo.setLocale(LocaleContextHolder.getLocale().toString());
		return Response.success(articleLabelService.page(qo, pageable));
	}

}