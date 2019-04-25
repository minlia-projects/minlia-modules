package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.ro.ArticleCommentQRO;
import com.minlia.module.article.ro.ArticleCommentCRO;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.service.ArticleCommentService;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Article Comment", description = "文章-评论")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/comment")
public class ArticleCommentEndpoint {

	@Autowired
	private ArticleCommentService articleCommentService;

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody ArticleCommentCRO cto) {
		return Response.success(articleCommentService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		articleCommentService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.READ + "')")
	@ApiOperation(value = "我的", notes = "我的", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "me", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response me(@PageableDefault Pageable pageable, @RequestBody ArticleCommentQRO qo) {
		qo.setCreateBy(SecurityContextHolder.getCurrentGuid());
		return Response.success(articleCommentService.queryMyPage(qo, pageable));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response id(@PathVariable Long id) {
		return Response.success(articleCommentService.queryById(id));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response count(@RequestBody ArticleCommentQRO qo) {
		return Response.success(articleCommentService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleCommentQRO qo) {
		return Response.success(articleCommentService.queryDetailsList(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleCommentQRO qo) {
		return Response.success(articleCommentService.queryDetailsPage(qo, pageable));
	}

}