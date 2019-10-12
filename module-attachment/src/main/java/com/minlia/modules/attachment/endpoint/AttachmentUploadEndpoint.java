package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.ro.AttachmentUploadBody;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Map;

/**
 * Created by will on 6/28/17.
 */
@Api(tags = "System Attachment", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/attachment/upload")
public class AttachmentUploadEndpoint {

    @Autowired
    private AttachmentUploadService attachmentUploadService;

    @ApiOperation(value = "上传", notes = "上传", httpMethod = "POST")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response upload(MultipartFile file) throws Exception {
        return attachmentUploadService.upload(file);
    }

    @ApiOperation(value = "上传", notes = "上传", httpMethod = "POST")
    @PostMapping(value = "base64", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response upload(@RequestBody AttachmentUploadBody uploadBody) throws Exception {
        //将字符串转换为byte数组
        byte[] bytes = new BASE64Decoder().decodeBuffer(uploadBody.getFile().trim());
        //转化为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        uploadBody.setInputStream(inputStream);
        uploadBody.setSize(Long.valueOf(bytes.length));
        return attachmentUploadService.uploadByBase64ToLocal(uploadBody, null, null);
    }

    @ApiOperation(value = "上传", notes = "上传", httpMethod = "POST")
    @PostMapping(value = "/{relationId}/{belongsTo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response upload(MultipartFile file, @PathVariable String relationId, @PathVariable String belongsTo) throws Exception {
        //检查是否满足上传条件 TODO 上传数量、belongsTo是否存在
//        String key = keyGenerate(file,relationId,belongsTo);
//        AttachmentDTO ossFile=null;
//        try {
//            ossFile = ossService.upload(file, key);
//        } catch (Exception e) {
//            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
//        }
//        Attachment attachment = Attachment.builder().belongsTo(belongsTo).relationId(relationId).name(ossFile.getOriginalName()).type(ossFile.getContentType()).url(ossFile.getUrl()).size(ossFile.getSize()).accessKey(ossFile.geteTag()).build();

        //附件记录
        return attachmentUploadService.upload(file, relationId, belongsTo);
    }

    public static void main(String[] agrs) throws IOException {
        byte[] data = null;

        // 读取图片字节数组
        try {

            InputStream in = new FileInputStream("/Users/garen/Pictures/下载.jpeg");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data)); // 返回Base64编码过的字节数组字符串


        byte[] bytes = new BASE64Decoder().decodeBuffer(encoder.encode(data).trim());
        //转化为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        FileUtils.copyInputStreamToFile(inputStream, new File("static/下载1.jpeg"));

        String absolutePath = new File("static/下载1.jpeg").getAbsolutePath();
        System.out.println(absolutePath);
    }

}
