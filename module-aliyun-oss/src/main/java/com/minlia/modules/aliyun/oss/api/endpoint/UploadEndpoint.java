package com.minlia.modules.aliyun.oss.api.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.aliyun.oss.api.body.MtsRequestBody;
import com.minlia.modules.aliyun.oss.api.body.MtsResponseBody;
import com.minlia.modules.aliyun.oss.api.body.UploadResponseBody;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssProperties;
import com.minlia.modules.aliyun.oss.api.constant.UploadCode;
import com.minlia.modules.aliyun.oss.api.enumeration.MtsTemplateType;
import com.minlia.modules.aliyun.oss.api.service.MtsService;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.aliyun.oss.builder.PathBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "upload", description = "文件上传服务")
public class UploadEndpoint {

    @Autowired
    private AliyunOssProperties properties;
    @Autowired
    ResourceLoader resourceloader;
    @Resource(type = OssService.class)
    private OssService ossService;
    @Resource(type = MtsService.class)
    private MtsService mtsService;

    public static final String RETURN_URL_PATTERN="//%s/%s";

    @PostMapping(value = "file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "文件上传服务", notes = "文件上传服务", httpMethod = "POST")
    public StatefulBody upload(MultipartFile file) throws Exception {
        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file);
        } catch (Exception e) {
            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
        }

        UploadResponseBody ret = new UploadResponseBody();
        ret.setCode(1);
        ret.setStatus(1);
        ret.setMessage(ossFile.getUrl());
        ret.setLink(ossFile.getUrl());
        return ret;
    }

    @ApiOperation(value = "视屏上传", notes = "视屏上传", httpMethod = "POST")
    @PostMapping(value = "video/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody video(MultipartFile file) throws Exception {
        //上传文件
        String inputObject=keyGenerateByContentType();

        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file,inputObject);
        } catch (Exception e) {
            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
        }
        String inputObjectUrl = ossFile.getUrl();

        //转码
        String outputObject = outputObjectGenerate(MtsTemplateType.MP4_HD);
        MtsResponseBody responseBody = mtsService.transcoding(MtsRequestBody.builder().inputObject(inputObject).outputObject(outputObject).mtsTemplateType(MtsTemplateType.MP4_HD).build());
        responseBody.setInputObjectUrl(inputObjectUrl);
        if(responseBody.getSuccess()){
            String outputObjectUrl=String.format(RETURN_URL_PATTERN,properties.getBucket(),properties.getEndpoint(),outputObject);
            responseBody.setOutputObjectUrl(outputObjectUrl);
            return SuccessResponseBody.builder().payload(responseBody).build();
        }else {
            return SuccessResponseBody.builder().payload(responseBody).code(2).build();
        }
    }

    @ApiOperation(value = "视屏转码", notes = "视屏转码", httpMethod = "POST")
    @PostMapping(value = "transcoding", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody transcoding(@Valid @RequestBody MtsRequestBody requestBody) throws Exception {
        mtsService.transcoding(requestBody);
        return SuccessResponseBody.builder().build();
    }

    private String outputObjectGenerate(MtsTemplateType mtsTemplateType){
        return PathBuilder.dateBuild() + UUID.randomUUID().toString() + "." + mtsTemplateType.getFormat();
    }

    private String keyGenerateByContentType(){
        return PathBuilder.dateBuild() + UUID.randomUUID().toString();
    }

}
