package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.qo.ArticleCommentQO;
import com.minlia.module.article.bean.to.ArticleCommentCTO;
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

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.CREATE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody ArticleCommentCTO cto) {
		return Response.success(articleCommentService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.DELETE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response delete(@PathVariable Long id) {
		articleCommentService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "我的", notes = "我的", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "me", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response me(@RequestBody ArticleCommentQO qo) {
		qo.setCreateBy(SecurityContextHolder.getCurrentGuid());
		return Response.success(articleCommentService.list(qo));
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
	public Response count(@RequestBody ArticleCommentQO qo) {
		return Response.success(articleCommentService.count(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody ArticleCommentQO qo) {
		return Response.success(articleCommentService.one(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleCommentQO qo) {
		return Response.success(articleCommentService.list(qo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleCommentQO qo) {
		return Response.success(articleCommentService.page(qo, pageable));
	}

}