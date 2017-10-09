//package com.minlia.module.attachment.v1;
//
//import com.minlia.cloud.body.impl.SuccessResponseBody;
//import com.minlia.cloud.constant.ApiPrefix;
//import com.minlia.cloud.utils.ApiPreconditions;
//import com.minlia.module.attachment.v1.domain.Attachment;
//import com.minlia.module.attachment.v1.service.AttachmentService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.util.UUID;
//
///**
// * Created by will on 6/28/17.
// * 对外提供的服务
// */
//@RestController
//@RequestMapping(value = ApiPrefix.API + "open/attachments")
//@Api(tags = "附件", description = "附件")
//@Slf4j
//public class AttachmentOpenApiEndpoint {
//
//    @Autowired
//    AttachmentService attachmentService; //设置权限点
//
//    @Autowired
//    private AliyunOssProperties properties; //获取阿里云对象的所有属性
//
//    @Resource(type = OssService.class) //获取阿里云
//    private OssService ossService;// 获取阿里云服务
//
//    public static final String RETURN_URL_PATTERN = "https://%s.%s/%s";
//
//    @ApiOperation(value = "文件上传服务", notes = "文件上传服务", httpMethod = "POST")
//    @PostMapping(value = "file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody upload(MultipartFile file) throws Exception {
//        String key=keyGenerate(file);
//        try {
//            ossService.upload(file,key);
//        } catch (Exception e) {
//            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage(), false);
//        }
//        String uri=String.format(RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),key);
//
//        //TODO 创建一条附件记录
//        Attachment attachment = Attachment.builder()
//                .businessType("")
//                .businessId("")
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .url(uri)
//                .size(file.getSize())
//                .accessToken(UUID.randomUUID().toString())
//                .build();
//        attachmentService.create(attachment);
//        return SuccessResponseBody.builder().message(uri).payload(attachment).build();
//    }
//
//    private String keyGenerate(MultipartFile file){
//        UuidFileNameBuilder builder = new UuidFileNameBuilder();
//        PathBuilder pathBuilder=new DatePathBuilder();
//        String key = pathBuilder.build() +builder.build(file.getOriginalFilename());
//        return key;
//    }
//
//}
