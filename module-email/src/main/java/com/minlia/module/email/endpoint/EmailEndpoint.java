package com.minlia.module.email.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.email.constant.EmailConstants;
import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.email.ro.EmailHtmlRO;
import com.minlia.module.email.ro.EmailRecordQRO;
import com.minlia.module.email.ro.EmailRichtextRO;
import com.minlia.module.email.service.EmailRecordService;
import com.minlia.module.email.service.EmailService;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "System Email", description = "邮件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "email")
public class EmailEndpoint {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailRecordService emailRecordService;

//    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.CREATE + "')")
//	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response create(@Valid @RequestBody SimpleMailMessage mailMessage) {
////		emailService.sendSimpleMail(mailMessage.getTo(), mailMessage.getSubject(), mailMessage.getText());
//		Map vars = Maps.newHashMap();
//		vars.put("code","8888");
//		emailService.sendTemplateMail( new String[]{"191285052@qq.com"}, "蜂鸟注册验证码", "captcha" , vars);
//		return Response.success();
//	}

    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.CREATE + "')")
	@ApiOperation(value = "HTML")
	@PostMapping(value = "html")
	public Response html(@Valid @RequestBody EmailHtmlRO ro) {
		EmailRecord emailRecord = emailService.sendHtmlMail(ro.getTo(), ro.getSubject(), ro.getContent(), null, null, LocaleEnum.valueOf(LocaleContextHolder.getLocale().toString()));
		return Response.success(emailRecord);
	}

    @PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.CREATE + "')")
	@ApiOperation(value = "RICKTEXT")
	@PostMapping(value = "ricktext")
	public Response richtext(@Valid @RequestBody EmailRichtextRO ro) {
		EmailRecord emailRecord = emailService.sendRichtextMail(ro.getTo(), ro.getTemplateCode(), ro.getVariables());
		return Response.success(emailRecord);
	}

	@AuditLog(value = "query one email sent record by number", type = OperationTypeEnum.INFO)
	@PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.SEARCH + "')")
	@ApiOperation(value = "编号查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "record/{number}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response one(@PathVariable String number) {
		EmailRecord x = emailRecordService.selectOneByNumber(number);
		return Response.success(x);
	}

	@AuditLog(value = "query email sent records as list", type = OperationTypeEnum.INFO)
	@PreAuthorize(value = "hasAnyAuthority('" + EmailConstants.SEARCH + "')")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "record/list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody EmailRecordQRO emailRecord) {
		List<EmailRecord> bankDos = emailRecordService.selectByAll(emailRecord);
		return Response.success(bankDos);
	}

//	@AuditLog(value = "query email sent records as paginated result")
//	@PreAuthorize(value = "isAuthenticated()")
//	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "record/page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Response paginated(EmailRecord emailRecord) {
//		PageInfo pageInfo = emailRecordService.page(emailRecord);
//		return Response.success(pageInfo);
//	}

}