package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        return attachmentUploadService.upload(file,relationId,belongsTo);
    }

}
