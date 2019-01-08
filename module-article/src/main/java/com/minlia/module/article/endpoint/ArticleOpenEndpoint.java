package com.minlia.module.article.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.vo.ArticleVO;
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

@Api(tags = "System Article Open", description = "文章-公开")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article")
public class ArticleOpenEndpoint {

	@Autowired
	private ArticleService articleService;

	@ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response findByNumber(@PathVariable Long id) {
		ArticleVO articleVO = articleService.oneVO(ArticleQO.builder().id(id).enabled(true).build());
		ApiAssert.notNull(articleVO, SystemCode.Message.DATA_NOT_EXISTS);
		//每次查询阅读数加1
		articleService.plusReadCount(id, 1);
		return Response.success(articleVO);
	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
//	@ApiOperation(value = "计数查询", notes = "计数查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "count", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response count(@RequestBody ArticleQO qo) {
//		return Response.success(articleService.count(qo));
//	}
//
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "one", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody ArticleQO qo) {
		qo.setEnabled(true);
		return Response.success(articleService.oneVO(qo));
	}

	@ApiOperation(value = "集合查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody ArticleQO qo) {
		qo.setEnabled(true);
		return Response.success(articleService.listVO(qo));
	}

	@ApiOperation(value = "分页查询", notes = "编号查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response page(@PageableDefault Pageable pageable, @RequestBody ArticleQO qo) {
		qo.setEnabled(true);
		return Response.success(articleService.pageVO(qo, pageable));
	}

}