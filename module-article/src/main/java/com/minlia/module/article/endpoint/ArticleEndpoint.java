package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticleCRO;
import com.minlia.module.article.ro.ArticleSetLabelRO;
import com.minlia.module.article.ro.ArticleURO;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Article", description = "文章")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article")
public class ArticleEndpoint {

	@Autowired
	private ArticleService articleService;

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody ArticleCRO cto) {
		return Response.success(articleService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.UPDATE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody ArticleURO uto) {
		return Response.success(articleService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		articleService.delete(id);
		return Response.success(SystemCode.Message.DELETE_SUCCESS);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.CREATE + "')")
	@ApiOperation(value = "设置标签", notes = "设置标签", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "set/labels", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response setLabels(@Valid @RequestBody ArticleSetLabelRO to) {
		return articleService.setLabels(to);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(articleService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody ArticleQRO qo) {
		return Response.success(articleService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody ArticleQRO qo) {
		return Response.success(articleService.oneVO(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleQRO qo) {
		return Response.success(articleService.listVO(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleQRO qo) {
		return Response.success(articleService.pageVO(qo, pageable));
	}

}