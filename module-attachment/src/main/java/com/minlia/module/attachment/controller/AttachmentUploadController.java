package com.minlia.module.attachment.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.attachment.bean.AttachmentUploadBody;
import com.minlia.module.attachment.constant.SysAttachmentConstant;
import com.minlia.module.attachment.service.AttachmentUploadService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

/**
 * <p>
 * 附件 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Api(tags = "System Attachment Upload", description = "附件-上传")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/attachment/upload")
public class AttachmentUploadController {

    private final AttachmentUploadService attachmentUploadService;

    public AttachmentUploadController(AttachmentUploadService attachmentUploadService) {
        this.attachmentUploadService = attachmentUploadService;
    }

    @AuditLog(value = "upload attachemnt by spring", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.UPLOAD + "')")
    @ApiOperation(value = "上传")
    @PostMapping(value = "")
    public Response upload(MultipartFile file) throws Exception {
        return attachmentUploadService.upload(file);
    }

    @AuditLog(value = "upload attachemnt by base64", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.UPLOAD + "')")
    @ApiOperation(value = "上传")
    @PostMapping(value = "base64")
    public Response upload(@RequestBody AttachmentUploadBody uploadBody) throws Exception {
        //将字符串转换为byte数组
        byte[] bytes = Base64.decodeBase64(uploadBody.getFile().trim());
        //转化为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        uploadBody.setInputStream(inputStream);
        uploadBody.setSize(Long.valueOf(bytes.length));
        return attachmentUploadService.uploadByBase64ToLocal(uploadBody, null, null);
    }

    @AuditLog(value = "upload attachemnt by spring with relation", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysAttachmentConstant.UPLOAD + "')")
    @ApiOperation(value = "上传")
    @PostMapping(value = "{relationId}/{relationTo}")
    public Response upload(MultipartFile file, @PathVariable String relationId, @PathVariable String relationTo) throws Exception {
        return attachmentUploadService.upload(file, relationId, relationTo);
    }

//    public static void main(String[] agrs) throws IOException {
//        byte[] data = null;
//
//        // 读取图片字节数组
//        try {
//
//            InputStream in = new FileInputStream("/Users/garen/Pictures/下载.jpeg");
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        System.out.println(encoder.encode(data)); // 返回Base64编码过的字节数组字符串
//
//
//        byte[] bytes = new BASE64Decoder().decodeBuffer(encoder.encode(data).trim());
//        //转化为输入流
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        FileUtils.copyInputStreamToFile(inputStream, new File("static/下载1.jpeg"));
//
//        String absolutePath = new File("static/下载1.jpeg").getAbsolutePath();
//        System.out.println(absolutePath);
//    }

}
