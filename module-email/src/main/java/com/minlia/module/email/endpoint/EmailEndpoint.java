package com.minlia.module.email.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.email.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "System Email", description = "邮件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "email")
public class EmailEndpoint {

	@Autowired
	private EmailService emailService;
	
//    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.CREATE + "')")
//	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response create(@Valid @RequestBody SimpleMailMessage mailMessage) {
////		emailService.sendSimpleMail(mailMessage.getTo(), mailMessage.getSubject(), mailMessage.getText());
//
//		Map vars = Maps.newHashMap();
//		vars.put("code","8888");
//		emailService.sendTemplateMail( new String[]{"191285052@qq.com"}, "蜂鸟注册验证码", "captcha" , vars);
//		return Response.success();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.UPDATE + "')")
//	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody update(@Valid @RequestBody EmailRecord bankDo) {
//		return SuccessResponseBody.builder().payload(emailService.update(bankDo)).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.DELETE + "')")
//	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody update(@PathVariable String number) {
//		emailService.delete(number);
//		return SuccessResponseBody.builder().build();
//	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "one", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@RequestBody EmailRecord emailRecord) {
		EmailRecord x = emailService.one(emailRecord);
		return Response.success(x);
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody EmailRecord emailRecord) {
		List<EmailRecord> bankDos = emailService.list(emailRecord);
		return Response.success(bankDos);
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response paginated(@PageableDefault Pageable pageable, EmailRecord emailRecord) {
		PageInfo pageInfo = emailService.page(emailRecord, pageable);
		return Response.success(pageInfo);
	}

}