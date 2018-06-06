package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.body.AttachmentUploadRequestBody;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by will on 6/28/17.
 */
@Api(tags = "System Attachment", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/attachment/upload")
public class AttachmentUploadEndpoint {

//    @Autowired
//    private OssService ossService;

    @Autowired
    private AttachmentUploadService attachmentUploadService;

//    @ApiOperation(value = "上传", notes = "文件上传服务", httpMethod = "POST")
//    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody upload(@RequestBody AttachmentUploadRequestBody requestBody) throws Exception {
//
//        return null;
//    }


    @ApiOperation(value = "上传", notes = "文件上传服务", httpMethod = "POST")
    @PostMapping(value = "/{relationId}/{belongsTo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody upload(MultipartFile file, @PathVariable String relationId, @PathVariable String belongsTo) throws Exception {
        //检查是否满足上传条件 TODO 上传数量、belongsTo是否存在

//        String key = keyGenerate(file,relationId,belongsTo);
//        OssFile ossFile=null;
//        try {
//            ossFile = ossService.upload(file, key);
//        } catch (Exception e) {
//            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
//        }
//        Attachment attachment = Attachment.builder().belongsTo(belongsTo).relationId(relationId).name(ossFile.getOriginalName()).type(ossFile.getContentType()).url(ossFile.getUrl()).size(ossFile.getSize()).accessKey(ossFile.geteTag()).build();

        //附件记录
        return attachmentUploadService.upload(file,relationId,belongsTo);
    }

//    @ApiOperation(value = "file", notes = "文件上传服务", httpMethod = "POST")
//    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody upload(MultipartFile file) throws Exception {
//        String key = keyGenerate(file);
//        OssFile ossFile=null;
//        try {
//            ossFile = ossService.upload(file, key);
//        } catch (Exception e) {
//            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
//        }
//        return  SuccessResponseBody.builder().message("上传成功").payload(ossFile).build();
//    }
//
//    private String keyGenerate(MultipartFile file){
//        return String.format("%s", PathBuilder.uuidNameBuild(file.getOriginalFilename()));
//    }
//
//    private String keyGenerate(MultipartFile file, String relationId, String belongsTo){
//        return String.format("%s/%s/%s",relationId,belongsTo, PathBuilder.uuidNameBuild(file.getOriginalFilename()));
//    }

}
